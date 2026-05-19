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

@AndroidEntryPoint
class MonitoringService : Service() {

    @Inject lateinit var prefs: UserPreferences
    @Inject lateinit var repo: MonitoringRepository

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var currentSessionPackage: String? = null
    private var sessionStartTime: Long = 0L
    private var breakFireable = true

    override fun onCreate() {
        super.onCreate()
        repo.isServiceRunning = true
        val notification = buildNotification()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(NOTIF_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            startForeground(NOTIF_ID, notification)
        }
        WatchdogWorker.enqueue(this)
        AlarmScheduler.schedule(this)   // exact-alarm keep-alive (Doze-immune)
        startPolling()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // When Android restarts the service via START_STICKY (intent == null),
        // verify Monitor Me is still enabled before continuing.
        if (intent == null) {
            scope.launch {
                if (!prefs.snapshotMonitorMeEnabled()) {
                    Log.d(TAG, "service: Monitor Me disabled — stopping self")
                    stopSelf()
                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        repo.isServiceRunning = false
        scope.cancel()
        // Alarm is intentionally left running — KeepAliveReceiver will restart the
        // service unless Monitor Me is OFF. If Monitor Me is OFF, the receiver
        // checks and skips. AlarmScheduler.cancel() is called explicitly by SettingsViewModel.
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startPolling() {
        scope.launch {
            while (isActive) {
                try { onPoll() } catch (e: Exception) {
                    Log.e(TAG, "poll error: ${e.message}")
                }
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

        // Read visit limit on every poll so Settings changes are picked up immediately.
        val maxMin = prefs.snapshotMaxMinutes()
        // Query window = at least double the visit limit, minimum 10 minutes.
        val windowMs = maxOf(maxMin * 60_000L * 2, 600_000L)

        val foreground = getForegroundPackage(windowMs)
        Log.d(TAG, "poll: foreground=$foreground  monitored=$monitored  session=$currentSessionPackage")

        if (foreground == null || foreground !in monitored) {
            if (currentSessionPackage != null) {
                currentSessionPackage = null
                breakFireable = true
            }
            return
        }

        if (foreground != currentSessionPackage) {
            currentSessionPackage = foreground
            sessionStartTime = System.currentTimeMillis()
            breakFireable = true
            Log.d(TAG, "poll: session started for $foreground")
            return
        }

        val elapsedSec = (System.currentTimeMillis() - sessionStartTime) / 1_000
        Log.d(TAG, "poll: ${elapsedSec}s elapsed, limit=${maxMin}min, fireable=$breakFireable")

        if (elapsedSec >= maxMin * 60L && breakFireable) {
            breakFireable = false
            Log.d(TAG, "poll: FIRING break overlay!")
            fireBreakOverlay()
        }
    }

    private suspend fun fireBreakOverlay() {
        val catId = prefs.snapshotActiveCatId()
        val breakMin = prefs.snapshotBreakMinutes()
        startActivity(
            Intent(this, BreakOverlayActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra(BreakOverlayActivity.EXTRA_CAT_ID, catId)
                .putExtra(BreakOverlayActivity.EXTRA_BREAK_MINUTES, breakMin)
        )
    }

    /**
     * Returns the package with the most recent ACTIVITY_RESUMED event.
     * [windowMs] is read from the configured visit limit (2× the limit, min 10 min)
     * so the event stays in-window for the entire session duration.
     */
    private fun getForegroundPackage(windowMs: Long): String? {
        val usm = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val now = System.currentTimeMillis()
        val events = usm.queryEvents(now - windowMs, now) ?: return null

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
                NotificationChannel(channelId, getString(R.string.notification_monitoring_title),
                    NotificationManager.IMPORTANCE_MIN).apply { setShowBadge(false) }
            )
        }
        val openIntent = PendingIntent.getActivity(
            this, 0,
            packageManager.getLaunchIntentForPackage(packageName),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
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
    }

    companion object {
        private const val NOTIF_ID = 1001
    }
}
