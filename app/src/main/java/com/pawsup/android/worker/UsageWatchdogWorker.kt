package com.pawsup.android.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.pawsup.android.data.repository.SettingsRepository
import com.pawsup.android.service.ensureMonitoringServiceRunning
import com.pawsup.android.util.PermissionHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class UsageWatchdogWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val settingsRepository: SettingsRepository,
    private val permissionHelper: PermissionHelper,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        ensureMonitoringServiceRunning(
            applicationContext,
            settingsRepository,
            permissionHelper,
        )
        return Result.success()
    }
}

object UsageWatchdogScheduler {
    private const val NAME = "pawsup_watchdog"

    fun schedule(context: Context) {
        val req = PeriodicWorkRequestBuilder<UsageWatchdogWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            req,
        )
    }
}
