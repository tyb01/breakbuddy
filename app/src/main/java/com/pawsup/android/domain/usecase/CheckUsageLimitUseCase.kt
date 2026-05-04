package com.pawsup.android.domain.usecase

import com.pawsup.android.data.datastore.UserPreferences
import com.pawsup.android.data.repository.SettingsRepository
import com.pawsup.android.data.repository.UsageRepository
import javax.inject.Inject

class CheckUsageLimitUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val usageRepository: UsageRepository,
) {

    suspend fun shouldEnforceBreak(
        prefs: UserPreferences,
        foregroundPackage: String?,
    ): Boolean {
        if (foregroundPackage == null) return false
        val enabled = settingsRepository.listAppSettings()
            .any { it.packageName == foregroundPackage && it.enabled }
        if (!enabled) return false
        val limitMs = prefs.usageLimitMinutes * 60_000L
        return usageRepository.currentSessionMs(foregroundPackage) >= limitMs
    }
}
