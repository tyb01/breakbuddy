package com.pawsup.android.data.repository

import android.content.Context
import android.content.pm.PackageManager
import com.pawsup.android.data.datastore.SettingsDataStore
import com.pawsup.android.data.datastore.ThemeMode
import com.pawsup.android.data.datastore.UserAppOverrides
import com.pawsup.android.data.datastore.UserPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsDataStore: SettingsDataStore,
) {
    val preferences: Flow<UserPreferences> = settingsDataStore.preferencesFlow

    suspend fun snapshotPrefs(): UserPreferences = settingsDataStore.snapshot()

    suspend fun setOnboardingComplete() = settingsDataStore.setOnboardingComplete()

    suspend fun setServiceEnabled(enabled: Boolean) = settingsDataStore.setServiceEnabled(enabled)

    suspend fun updateLimits(usageLimitMin: Int, breakMin: Int) =
        settingsDataStore.updateLimits(usageLimitMin, breakMin)

    suspend fun updateAppOverride(pkg: String, override: UserAppOverrides) =
        settingsDataStore.updateOverride(pkg, override)

    val oemBatteryPrompted: Flow<Boolean> = settingsDataStore.oemBatteryPromptedFlow

    suspend fun setOemBatteryPrompted() = settingsDataStore.setOemBatteryPrompted()

    suspend fun setThemeMode(mode: ThemeMode) = settingsDataStore.setThemeMode(mode)

    suspend fun listAppSettings(): List<com.pawsup.android.data.model.AppSetting> =
        settingsDataStore.buildAppSettings { pkg ->
            labelForPackage(pkg)
        }

    private fun labelForPackage(pkg: String): String? = runCatching {
        val pm = context.packageManager
        val appInfo = pm.getApplicationInfo(pkg, 0)
        pm.getApplicationLabel(appInfo).toString()
    }.getOrNull()
}
