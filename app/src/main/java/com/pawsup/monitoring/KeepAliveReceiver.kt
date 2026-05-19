package com.pawsup.monitoring

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.pawsup.data.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PawsUp"

/**
 * Fires on the chain-scheduled alarm. Checks Monitor Me, restarts the service
 * if it's dead, then reschedules the next alarm. Handles both the keep-alive alarm
 * and device boot / package-replaced broadcasts.
 */
@AndroidEntryPoint
class KeepAliveReceiver : BroadcastReceiver() {

    @Inject lateinit var prefs: UserPreferences
    @Inject lateinit var repo: MonitoringRepository

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: ""

        // Ignore irrelevant actions
        if (action.isNotEmpty() &&
            action != Intent.ACTION_BOOT_COMPLETED &&
            action != Intent.ACTION_MY_PACKAGE_REPLACED &&
            action != "com.pawsup.KEEP_ALIVE") return

        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (!prefs.snapshotMonitorMeEnabled()) {
                    Log.d(TAG, "keep-alive: Monitor Me OFF — skipping")
                    return@launch
                }

                if (!repo.isServiceRunning) {
                    Log.d(TAG, "keep-alive: service dead — restarting")
                    ContextCompat.startForegroundService(
                        context,
                        Intent(context, MonitoringService::class.java)
                    )
                } else {
                    Log.d(TAG, "keep-alive: service already running — no action")
                }

                // Reschedule the next alarm (chain pattern)
                AlarmScheduler.schedule(context)

            } finally {
                pending.finish()
            }
        }
    }
}
