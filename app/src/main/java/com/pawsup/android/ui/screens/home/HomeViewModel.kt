package com.pawsup.android.ui.screens.home

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawsup.android.data.datastore.UserAppOverrides
import com.pawsup.android.data.datastore.UserPreferences
import com.pawsup.android.data.repository.SettingsRepository
import com.pawsup.android.data.repository.UsageRepository
import com.pawsup.android.domain.usecase.SaveSettingsUseCase
import com.pawsup.android.service.UsageMonitorService
import com.pawsup.android.util.PermissionHelper
import com.pawsup.android.worker.UsageWatchdogScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class TrackedAppRow(
    val packageName: String,
    val displayName: String,
    val iconRes: Int,
    val enabled: Boolean,
    val sessionMs: Long,
    val limitMs: Long,
)

data class HomeUiState(
    val prefs: UserPreferences,
    val appRows: List<TrackedAppRow>,
    val serviceRunning: Boolean,
    val usageAccessOk: Boolean,
    val overlayOk: Boolean,
    val batteryOptimizationsIgnored: Boolean,
    val oemAutoStartAvailable: Boolean,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val app: Context,
    private val settingsRepository: SettingsRepository,
    private val usageRepository: UsageRepository,
    private val saveSettings: SaveSettingsUseCase,
    private val permissionHelper: PermissionHelper,
) : ViewModel() {

    private val _ui = MutableStateFlow<HomeUiState?>(null)
    val ui: StateFlow<HomeUiState?> = _ui.asStateFlow()

    private val _showSaved = MutableStateFlow(false)
    val showSaved: StateFlow<Boolean> = _showSaved.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.preferences,
                flow {
                    emit(Unit)
                    while (true) {
                        delay(1_000)
                        emit(Unit)
                    }
                },
            ) { prefs, _ -> prefs }
                .collectLatest { prefs ->
                    _ui.value = buildState(prefs)
                }
        }
    }

    private suspend fun buildState(prefs: UserPreferences): HomeUiState {
        val limitMs = prefs.usageLimitMinutes * 60_000L
        val apps = settingsRepository.listAppSettings()
        val rows = apps.map { a ->
            TrackedAppRow(
                packageName = a.packageName,
                displayName = a.displayName,
                iconRes = a.iconRes,
                enabled = a.enabled,
                sessionMs = usageRepository.currentSessionMs(a.packageName),
                limitMs = limitMs,
            )
        }
        return HomeUiState(
            prefs = prefs,
            appRows = rows,
            serviceRunning = UsageMonitorService.isRunning && prefs.serviceEnabled,
            usageAccessOk = permissionHelper.hasUsageStatsPermission(),
            overlayOk = permissionHelper.hasOverlayPermission(),
            batteryOptimizationsIgnored = permissionHelper.isIgnoringBatteryOptimizations(),
            oemAutoStartAvailable = permissionHelper.hasOemAutoStartSettings(),
        )
    }

    fun setMonitoring(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setServiceEnabled(enabled)
            if (enabled) {
                UsageMonitorService.start(app)
                UsageWatchdogScheduler.schedule(app)
                val prompted = settingsRepository.oemBatteryPrompted.first()
                if (!prompted && !permissionHelper.isIgnoringBatteryOptimizations()) {
                    permissionHelper.requestIgnoreBatteryOptimizations()
                    settingsRepository.setOemBatteryPrompted()
                }
            } else {
                app.stopService(Intent(app, UsageMonitorService::class.java))
            }
        }
    }

    fun saveLimits(usageLimitMin: Int, breakMin: Int) {
        viewModelScope.launch {
            saveSettings.updateLimits(usageLimitMin, breakMin)
            _showSaved.value = true
            delay(1_600)
            _showSaved.value = false
        }
    }

    fun setAppEnabled(pkg: String, enabled: Boolean) {
        viewModelScope.launch {
            saveSettings.setAppOverride(pkg, UserAppOverrides(enabled = enabled))
        }
    }

    fun openUsageAccessSettings() {
        permissionHelper.openUsageAccessSettings()
    }

    fun openOverlaySettings() {
        permissionHelper.openOverlaySettings()
    }

    fun requestBatteryExemption() {
        viewModelScope.launch {
            if (!permissionHelper.isIgnoringBatteryOptimizations()) {
                permissionHelper.requestIgnoreBatteryOptimizations()
                settingsRepository.setOemBatteryPrompted()
            }
        }
    }

    fun openOemAutoStartSettings() {
        permissionHelper.openOemAutoStartSettings()
    }
}
