package com.pawsup.android.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pawsup.android.data.model.AppSetting
import com.pawsup.android.data.model.DefaultTrackedApps
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONObject

data class UserAppOverrides(
    val enabled: Boolean = true,
)

data class UserPreferences(
    val onboardingComplete: Boolean,
    val serviceEnabled: Boolean,
    /** Minutes of continuous in-app time before a break (extension “usage limit”). */
    val usageLimitMinutes: Int,
    val breakDurationMinutes: Int,
    val overrides: Map<String, UserAppOverrides>,
    val themeMode: ThemeMode,
)

@Singleton
class SettingsDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    private object Keys {
        val ONBOARDING = booleanPreferencesKey("onboarding_complete")
        val SERVICE = booleanPreferencesKey("service_enabled")
        val LIMIT = intPreferencesKey("daily_limit_minutes")
        val BREAK = intPreferencesKey("break_duration_minutes")
        val OVERRIDES = stringPreferencesKey("app_overrides_json")
        val OEM_BATTERY_PROMPTED = booleanPreferencesKey("oem_battery_prompted")
        val THEME_MODE = intPreferencesKey("theme_mode")
    }

    val preferencesFlow: Flow<UserPreferences> = dataStore.data.map { p ->
        UserPreferences(
            onboardingComplete = p[Keys.ONBOARDING] ?: false,
            serviceEnabled = p[Keys.SERVICE] ?: false,
            usageLimitMinutes = p[Keys.LIMIT] ?: 60,
            breakDurationMinutes = p[Keys.BREAK] ?: 5,
            overrides = parseOverrides(p[Keys.OVERRIDES]),
            themeMode = ThemeMode.fromOrdinal(p[Keys.THEME_MODE] ?: ThemeMode.LIGHT.ordinal),
        )
    }

    suspend fun snapshot(): UserPreferences = preferencesFlow.first()

    private fun parseOverrides(json: String?): Map<String, UserAppOverrides> {
        if (json.isNullOrBlank()) return emptyMap()
        return runCatching {
            val o = JSONObject(json)
            buildMap {
                val it = o.keys()
                while (it.hasNext()) {
                    val k = it.next()
                    val jo = o.getJSONObject(k)
                    put(k, UserAppOverrides(enabled = jo.optBoolean("enabled", true)))
                }
            }
        }.getOrElse { emptyMap() }
    }

    private fun overridesToJson(map: Map<String, UserAppOverrides>): String {
        val o = JSONObject()
        map.forEach { (k, v) ->
            o.put(k, JSONObject().put("enabled", v.enabled))
        }
        return o.toString()
    }

    suspend fun setOnboardingComplete() {
        dataStore.edit { it[Keys.ONBOARDING] = true }
    }

    suspend fun setServiceEnabled(enabled: Boolean) {
        dataStore.edit { it[Keys.SERVICE] = enabled }
    }

    suspend fun updateLimits(usageLimitMin: Int, breakMin: Int) {
        dataStore.edit {
            it[Keys.LIMIT] = usageLimitMin.coerceIn(1, 480)
            it[Keys.BREAK] = breakMin.coerceIn(1, 60)
        }
    }

    suspend fun updateOverride(pkg: String, override: UserAppOverrides) {
        dataStore.edit { prefs ->
            val cur = parseOverrides(prefs[Keys.OVERRIDES]).toMutableMap()
            cur[pkg] = override
            prefs[Keys.OVERRIDES] = overridesToJson(cur)
        }
    }

    suspend fun setOemBatteryPrompted() {
        dataStore.edit { it[Keys.OEM_BATTERY_PROMPTED] = true }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { it[Keys.THEME_MODE] = mode.ordinal }
    }

    val oemBatteryPromptedFlow: Flow<Boolean> = dataStore.data.map {
        it[Keys.OEM_BATTERY_PROMPTED] ?: false
    }

    suspend fun buildAppSettings(resolver: (String) -> String?): List<AppSetting> {
        val prefs = snapshot()
        return DefaultTrackedApps.ALL.mapNotNull { t ->
            val label = resolver(t.packageName) ?: return@mapNotNull null
            val ov = prefs.overrides[t.packageName] ?: UserAppOverrides()
            AppSetting(
                packageName = t.packageName,
                displayName = label,
                iconRes = t.iconRes,
                enabled = ov.enabled,
            )
        }
    }
}
