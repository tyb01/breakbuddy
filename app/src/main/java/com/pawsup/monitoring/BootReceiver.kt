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

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var prefs: UserPreferences

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (!prefs.snapshotMonitorMeEnabled()) {
                    Log.d(TAG, "boot: Monitor Me OFF — skipping")
                    return@launch
                }
                Log.d(TAG, "boot: starting service + alarm")
                ContextCompat.startForegroundService(
                    context,
                    Intent(context, MonitoringService::class.java)
                )
                // Re-arm both keep-alive layers after reboot
                AlarmScheduler.schedule(context)
                WatchdogWorker.enqueue(context)
            } finally {
                pending.finish()
            }
        }
    }
}
