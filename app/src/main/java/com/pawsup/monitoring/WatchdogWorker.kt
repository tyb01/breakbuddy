package com.pawsup.monitoring

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.pawsup.data.UserPreferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

private const val TAG = "PawsUp"

@HiltWorker
class WatchdogWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repo: MonitoringRepository,
    private val prefs: UserPreferences
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (!prefs.snapshotMonitorMeEnabled()) {
            Log.d(TAG, "watchdog: Monitor Me is OFF — skipping restart")
            return Result.success()
        }
        if (!repo.isServiceRunning) {
            Log.d(TAG, "watchdog: service not running — restarting")
            ContextCompat.startForegroundService(
                applicationContext,
                Intent(applicationContext, MonitoringService::class.java)
            )
        }
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "pawsup_watchdog"

        fun enqueue(context: Context) {
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequestBuilder<WatchdogWorker>(15, TimeUnit.MINUTES)
                    .setConstraints(Constraints.NONE)
                    .build()
            )
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }
    }
}
