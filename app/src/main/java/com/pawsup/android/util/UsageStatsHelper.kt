package com.pawsup.android.util

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsageStatsHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val usageStatsManager: UsageStatsManager?
        get() = context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager

    /**
     * Resolves which app is most likely in the foreground. Uses a long usage-events window so a
     * session that stays in one app (no new events for a while) still resolves to that app.
     */
    fun foregroundPackageName(): String? {
        val usm = usageStatsManager ?: return null
        val end = System.currentTimeMillis()
        val begin = end - TimeUnit.HOURS.toMillis(2)
        val events = usm.queryEvents(begin, end)
        var current: String? = null
        val e = UsageEvents.Event()
        while (events.hasNextEvent()) {
            events.getNextEvent(e)
            when (e.eventType) {
                UsageEvents.Event.MOVE_TO_FOREGROUND -> current = e.packageName
                UsageEvents.Event.MOVE_TO_BACKGROUND -> {
                    if (current == e.packageName) current = null
                }
                else -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        when (e.eventType) {
                            UsageEvents.Event.ACTIVITY_RESUMED -> current = e.packageName
                            UsageEvents.Event.ACTIVITY_PAUSED -> {
                                if (current == e.packageName) current = null
                            }
                        }
                    }
                }
            }
        }
        if (current != null) return current
        return fallbackForegroundFromStats(usm, end)
    }

    private fun fallbackForegroundFromStats(usm: UsageStatsManager, end: Long): String? {
        val begin = end - TimeUnit.MINUTES.toMillis(15)
        val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, begin, end) ?: return null
        val fresh = end - TimeUnit.SECONDS.toMillis(30)
        val best = stats
            .filter { it.lastTimeUsed >= fresh && it.totalTimeInForeground > 0L }
            .maxByOrNull { it.lastTimeUsed }
            ?.packageName
        if (best != null) return best
        return stats.maxByOrNull { it.lastTimeUsed }?.packageName
    }
}
