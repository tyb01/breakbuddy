package com.pawsup.android.service

import android.content.Context
import com.pawsup.android.data.repository.SettingsRepository
import com.pawsup.android.util.PermissionHelper
import com.pawsup.android.worker.UsageWatchdogScheduler

suspend fun ensureMonitoringServiceRunning(
    appContext: Context,
    settingsRepository: SettingsRepository,
    permissionHelper: PermissionHelper,
) {
    if (!settingsRepository.snapshotPrefs().serviceEnabled) return
    if (!permissionHelper.hasUsageStatsPermission()) return
    if (!permissionHelper.hasOverlayPermission()) return
    if (UsageMonitorService.isRunning) return
    UsageMonitorService.start(appContext)
    UsageWatchdogScheduler.schedule(appContext)
}
