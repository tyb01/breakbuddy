package com.pawsup.android.domain.usecase

import com.pawsup.android.data.datastore.UserAppOverrides
import com.pawsup.android.data.repository.SettingsRepository
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    suspend fun updateLimits(usageLimitMin: Int, breakMin: Int) =
        settingsRepository.updateLimits(usageLimitMin, breakMin)

    suspend fun setAppOverride(pkg: String, override: UserAppOverrides) =
        settingsRepository.updateAppOverride(pkg, override)
}
