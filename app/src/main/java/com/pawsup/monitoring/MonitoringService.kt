package com.pawsup.monitoring

import android.app.*
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.pawsup.R
import com.pawsup.break_experience.BreakOverlayActivity
import com.pawsup.data.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

private const val TAG = "PawsUp"

/**
 * ACTIVITY_RESUMED from UsageEvents can flicker (launcher / system packages win briefly during
 * gestures, recents, OEM quirks) even though the monitored app is still visibly in use. Delay
 * ending the active session until this many consecutive polls with no monitored foreground.
 * At 2s poll interval × 4 ≈ 8s genuinely away before the visit timer resets.
 */
private const val OUTSIDE_MONITORED_DEBOUNCE_POLLS = 4

@AndroidEntryPoint
class MonitoringService : Service() {

    @Inject lateinit var prefs: UserPreferences
    @Inject lateinit var repo: MonitoringRepository

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var currentSessionPackage: String? = null
    private var sessionStartTime: Long = 0L
    private var breakFireable = true
    private var outsideMonitoredStreak = 0

    override fun onCreate() {
        super.onCreate()
        repo.isServiceRunning = true
        val notification = buildNotification()
        // API 34+ requires explicit foreground service type in startForeground()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(NOTIF_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            startForeground(NOTIF_ID, notification)
        }
        WatchdogWorker.enqueue(this)
        startPolling()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onDestroy() {
        super.onDestroy()
        repo.isServiceRunning = false
        scope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startPolling() {
        scope.launch {
            while (isActive) {
                try { onPoll() } catch (e: Exception) { /* keep polling on any error */ }
                delay(2_000)
            }
        }
    }

    private suspend fun onPoll() {
        val monitored = prefs.snapshotMonitoredPackages()
        if (monitored.isEmpty()) {
            Log.d(TAG, "poll: no monitored packages")
            return
        }

        val visitLimitMinutes = prefs.snapshotMaxMinutes()
        val lookbackMs = maxOf(visitLimitMinutes * 60_000L * 2, 600_000L)
        val foreground = getForegroundPackage(lookbackMs)
        Log.d(TAG, "poll: foreground=$foreground  monitored=$monitored  session=$currentSessionPackage")

        if (foreground != null && foreground in monitored) {
            outsideMonitoredStreak = 0
            if (foreground != currentSessionPackage) {
                currentSessionPackage = foreground
                sessionStartTime = System.currentTimeMillis()
                breakFireable = true
                Log.d(TAG, "poll: session started for $foreground")
                return
            }

            val elapsedSec = (System.currentTimeMillis() - sessionStartTime) / 1_000
            Log.d(TAG, "poll: ${elapsedSec}s elapsed, limit=${visitLimitMinutes}min, fireable=$breakFireable")
            if (elapsedSec >= visitLimitMinutes * 60L && breakFireable) {
                breakFireable = false
                Log.d(TAG, "poll: FIRING break overlay!")
                fireBreakOverlay()
            }
            return
        }

        val session = currentSessionPackage
        if (session != null && session in monitored) {
            outsideMonitoredStreak++
            if (outsideMonitoredStreak < OUTSIDE_MONITORED_DEBOUNCE_POLLS) {
                val elapsedSec = (System.currentTimeMillis() - sessionStartTime) / 1_000
                Log.d(
                    TAG,
                    "poll: debouncing away (outside=$outsideMonitoredStreak) fg=$foreground ${elapsedSec}s limit=${visitLimitMinutes}min session=$session"
                )
                if (elapsedSec >= visitLimitMinutes * 60L && breakFireable) {
                    breakFireable = false
                    Log.d(TAG, "poll: FIRING break overlay!")
                    fireBreakOverlay()
                }
                return
            }
        }

        outsideMonitoredStreak = 0
        if (session != null) {
            currentSessionPackage = null
            breakFireable = true
        }
    }

    private suspend fun fireBreakOverlay() {
        val catId = prefs.snapshotActiveCatId()
        val breakMin = prefs.snapshotBreakMinutes()
        startActivity(
            Intent(this, BreakOverlayActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(BreakOverlayActivity.EXTRA_CAT_ID, catId)
                .putExtra(BreakOverlayActivity.EXTRA_BREAK_MINUTES, breakMin)
        )
    }

    /**
     * Returns the package with the most recent ACTIVITY_RESUMED event.
     * Deliberately ignores PAUSED events — apps like Instagram fire internal
     * PAUSED/RESUMED pairs (dialogs, fragments) that cause false negatives.
     * The poll loop debounces session end when foreground is briefly non-monitored (launcher/recents flicker).
     */
    private fun getForegroundPackage(lookbackMs: Long): String? {
        val usm = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val now = System.currentTimeMillis()
        val events = usm.queryEvents(now - lookbackMs, now) ?: return null

        var latestPkg: String? = null
        var latestTime = 0L

        val event = UsageEvents.Event()
        while (events.hasNextEvent()) {
            events.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.ACTIVITY_RESUMED &&
                event.timeStamp > latestTime) {
                latestPkg = event.packageName
                latestTime = event.timeStamp
            }
        }
        return latestPkg
    }

    private fun buildNotification(): Notification {
        val channelId = "pawsup_monitoring"
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (nm.getNotificationChannel(channelId) == null) {
            nm.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    getString(R.string.notification_monitoring_title),
                    NotificationManager.IMPORTANCE_MIN
                ).apply { setShowBadge(false) }
            )
        }
        val openIntent = PendingIntent.getActivity(
            this, 0,
            packageManager.getLaunchIntentForPackage(packageName),
            PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_cat)
            .setContentTitle(getString(R.string.notification_monitoring_title))
            .setContentText(getString(R.string.notification_monitoring_body))
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .setSilent(true)
            .setContentIntent(openIntent)
            .build()
    }

    fun resetSession() {
        currentSessionPackage = null
        breakFireable = true
        outsideMonitoredStreak = 0
    }

    companion object {
        private const val NOTIF_ID = 1001
    }
}
