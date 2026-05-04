package com.pawsup.android.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.pawsup.android.R
import com.pawsup.android.data.datastore.UserPreferences
import com.pawsup.android.data.repository.SettingsRepository
import com.pawsup.android.data.repository.UsageRepository
import com.pawsup.android.domain.usecase.CheckUsageLimitUseCase
import com.pawsup.android.ui.MainActivity
import com.pawsup.android.util.PermissionHelper
import com.pawsup.android.util.UsageStatsHelper
import com.pawsup.android.worker.UsageWatchdogScheduler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Foreground monitor: usage accrues only for user-selected apps. Session timers live in memory
 * ([UsageRepository]); preferences use on-device DataStore. No analytics, accounts, or server upload.
 *
 * Break overlay applies while the user stays in the app that triggered the limit. Switching away
 * (home, recents, or another app) ends the break immediately so the overlay does not block the rest
 * of the device.
 */
@AndroidEntryPoint
class UsageMonitorService : Service() {

    @Inject
    lateinit var usageStatsHelper: UsageStatsHelper

    @Inject
    lateinit var settingsRepository: SettingsRepository

    @Inject
    lateinit var usageRepository: UsageRepository

    @Inject
    lateinit var checkUsageLimitUseCase: CheckUsageLimitUseCase

    @Inject
    lateinit var overlayManager: OverlayManager

    @Inject
    lateinit var permissionHelper: PermissionHelper

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var pollJob: Job? = null

    private val breakLock = Any()
    private var breakTriggerPackage: String? = null

    @Volatile
    private var screenOn: Boolean = true

    @Volatile
    private var breakActive: Boolean = false

    private val screenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_SCREEN_OFF -> screenOn = false
                Intent.ACTION_SCREEN_ON, Intent.ACTION_USER_PRESENT -> screenOn = true
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        isRunning = true
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_USER_PRESENT)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(screenReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("DEPRECATION")
            registerReceiver(screenReceiver, filter)
        }
        UsageWatchdogScheduler.schedule(applicationContext)
    }

    override fun onDestroy() {
        isRunning = false
        pollJob?.cancel()
        runCatching { unregisterReceiver(screenReceiver) }
        synchronized(breakLock) {
            breakTriggerPackage = null
            breakActive = false
        }
        overlayManager.dismissOverlay()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PAUSE -> {
                scope.launch { settingsRepository.setServiceEnabled(false) }
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
                return START_NOT_STICKY
            }
        }

        createChannel()
        val prefs = runBlocking { settingsRepository.snapshotPrefs() }
        startForeground(NOTIFICATION_ID, buildMonitoringNotification(prefs, null, null))
        startPolling()
        return START_STICKY
    }

    private fun startPolling() {
        pollJob?.cancel()
        pollJob = scope.launch {
            var lastAccMs = 0L
            val ownPkg = packageName
            while (isActive) {
                val prefs = settingsRepository.snapshotPrefs()
                if (!prefs.serviceEnabled) break

                if (!permissionHelper.hasUsageStatsPermission() ||
                    !permissionHelper.hasOverlayPermission()
                ) {
                    lastAccMs = SystemClock.elapsedRealtime()
                    delay(POLL_MS)
                    continue
                }

                val fg = usageStatsHelper.foregroundPackageName()
                usageRepository.syncForeground(fg, ownPkg)

                val enabledKeys = settingsRepository.listAppSettings()
                    .filter { it.enabled }
                    .associateBy { it.packageName }

                val shouldEndBreakEarly = synchronized(breakLock) {
                    screenOn && breakActive && breakTriggerPackage != null && fg != breakTriggerPackage
                }
                if (shouldEndBreakEarly) {
                    finishBreak(overlayDismissAlreadyCalled = false)
                }

                val isBreak = synchronized(breakLock) { breakActive }
                if (!screenOn || isBreak) {
                    lastAccMs = SystemClock.elapsedRealtime()
                    val label = enabledKeys[fg]?.displayName
                    updateNotification(prefs, fg, label)
                    delay(POLL_MS)
                    continue
                }

                if (fg != null &&
                    enabledKeys.containsKey(fg)
                ) {
                    val now = SystemClock.elapsedRealtime()
                    val delta = if (lastAccMs == 0L) {
                        POLL_MS
                    } else {
                        (now - lastAccMs).coerceIn(POLL_MS / 2, POLL_MS * 4)
                    }
                    lastAccMs = now
                    usageRepository.addSessionMs(fg, delta)
                    if (checkUsageLimitUseCase.shouldEnforceBreak(prefs, fg)) {
                        beginBreak(prefs, fg)
                    }
                } else {
                    lastAccMs = SystemClock.elapsedRealtime()
                }

                val label = enabledKeys[fg]?.displayName
                updateNotification(prefs, fg, label)
                delay(POLL_MS)
            }
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    private fun finishBreak(overlayDismissAlreadyCalled: Boolean) {
        val pkg: String?
        synchronized(breakLock) {
            if (!breakActive) return
            pkg = breakTriggerPackage
            breakTriggerPackage = null
            breakActive = false
        }
        if (!overlayDismissAlreadyCalled) {
            overlayManager.dismissOverlay()
        }
        if (pkg != null) {
            usageRepository.resetSession(pkg)
        }
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .cancel(BREAK_NOTIFICATION_ID)
    }

    private fun beginBreak(prefs: UserPreferences, packageName: String) {
        synchronized(breakLock) {
            if (breakActive) return
            breakTriggerPackage = packageName
            breakActive = true
        }
        // Immediate zero so timing for the next visit cannot sit at the limit while the break UI runs.
        usageRepository.resetSession(packageName)
        if (overlayManager.isShowing()) {
            finishBreak(overlayDismissAlreadyCalled = false)
            return
        }
        val breakMin = prefs.breakDurationMinutes.coerceIn(1, 60)
        val breakSec = breakMin * 60
        val catLabel = getString(R.string.cat_display_name)
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(
            BREAK_NOTIFICATION_ID,
            buildBreakNotification(catLabel, breakSec),
        )
        overlayManager.showOverlay(
            breakDurationSeconds = breakSec,
            catName = catLabel,
        ) {
            finishBreak(overlayDismissAlreadyCalled = true)
        }
    }

    private fun createChannel() {
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_MAIN,
                    getString(R.string.notif_channel_main),
                    NotificationManager.IMPORTANCE_LOW,
                ),
            )
            nm.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_BREAK,
                    getString(R.string.notif_channel_break),
                    NotificationManager.IMPORTANCE_HIGH,
                ),
            )
        }
    }

    private fun updateNotification(
        prefs: UserPreferences,
        fg: String? = null,
        fgLabel: String? = null,
    ) {
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(NOTIFICATION_ID, buildMonitoringNotification(prefs, fg, fgLabel))
    }

    private fun buildMonitoringNotification(
        prefs: UserPreferences,
        fg: String?,
        fgLabel: String?,
    ): Notification {
        val sessionMs = if (fg != null) usageRepository.currentSessionMs(fg) else 0L
        val usedMin = (sessionMs / 60_000).toInt()
        val limitMin = prefs.usageLimitMinutes
        val remaining = (limitMin - usedMin).coerceAtLeast(0)

        val remote = RemoteViews(packageName, R.layout.notification_monitoring)
        val line1 = buildString {
            if (fgLabel != null) {
                append(fgLabel)
                append(" · ")
            }
            append(getString(R.string.notif_session_minutes, usedMin))
            append(" · ")
            append(getString(R.string.notif_session_remaining, remaining))
        }
        val cat = getString(R.string.cat_display_name)
        remote.setTextViewText(R.id.notif_line1, getString(R.string.notif_watching_title, cat))
        remote.setTextViewText(R.id.notif_line2, line1.ifBlank { getString(R.string.app_name) })

        val pause = PendingIntent.getService(
            this,
            0,
            Intent(this, UsageMonitorService::class.java).setAction(ACTION_PAUSE),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val settings = PendingIntent.getActivity(
            this,
            1,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        return NotificationCompat.Builder(this, CHANNEL_MAIN)
            .setSmallIcon(R.drawable.ic_launcher_cat)
            .setCustomContentView(remote)
            .setCustomBigContentView(remote)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentIntent(settings)
            .addAction(0, getString(R.string.notif_action_pause), pause)
            .addAction(0, getString(R.string.notif_action_settings), settings)
            .setOngoing(true)
            .build()
    }

    private fun buildBreakNotification(catName: String, seconds: Int): Notification {
        val name = catName.ifBlank { getString(R.string.cat_display_name) }
        val minutes = (seconds / 60).coerceAtLeast(1)
        val rv = RemoteViews(packageName, R.layout.notification_monitoring)
        rv.setTextViewText(R.id.notif_line1, getString(R.string.notif_break_title, name))
        rv.setTextViewText(
            R.id.notif_line2,
            resources.getQuantityString(
                R.plurals.notif_break_body_minutes,
                minutes,
                name,
                minutes,
            ),
        )
        return NotificationCompat.Builder(this, CHANNEL_BREAK)
            .setSmallIcon(R.drawable.ic_launcher_cat)
            .setCustomContentView(rv)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    companion object {
        const val ACTION_PAUSE = "com.pawsup.android.PAUSE_MONITORING"
        private const val CHANNEL_MAIN = "pawsup_main"
        private const val CHANNEL_BREAK = "pawsup_break"
        private const val NOTIFICATION_ID = 1001
        private const val BREAK_NOTIFICATION_ID = 1002
        internal const val POLL_MS = 1_000L

        @Volatile
        var isRunning: Boolean = false

        fun start(context: Context) {
            val i = Intent(context, UsageMonitorService::class.java)
            ContextCompat.startForegroundService(context, i)
        }
    }
}
