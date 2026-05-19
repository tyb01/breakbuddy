package com.pawsup.monitoring

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

/**
 * Schedules a chain-repeating exact alarm as a secondary keep-alive layer.
 * Fires even while the device is in Doze mode (unlike WorkManager periodic work
 * which can be deferred by hours on idle devices).
 *
 * Strategy:
 *  - API < 31: setExactAndAllowWhileIdle — always available, fires in Doze
 *  - API 31+ with exact alarm permission: setExactAndAllowWhileIdle
 *  - API 31+ without exact alarm permission: setAndAllowWhileIdle (inexact but Doze-immune)
 *
 * No SCHEDULE_EXACT_ALARM permission required — falls back gracefully.
 * Each alarm fires KeepAliveReceiver, which reschedules the next one (chain pattern).
 */
object AlarmScheduler {

    private const val INTERVAL_MS = 15 * 60 * 1_000L   // 15 minutes
    private const val REQUEST_CODE = 0x4B41             // "KA"

    fun schedule(context: Context) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pending = pendingIntent(context) ?: return
        val triggerAt = System.currentTimeMillis() + INTERVAL_MS

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !am.canScheduleExactAlarms()) {
            // No exact alarm grant — use inexact but Doze-immune variant
            am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pending)
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pending)
        }
    }

    fun cancel(context: Context) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pendingIntent(context)?.let { am.cancel(it) }
    }

    private fun pendingIntent(context: Context): PendingIntent? =
        PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            Intent(context, KeepAliveReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
}
