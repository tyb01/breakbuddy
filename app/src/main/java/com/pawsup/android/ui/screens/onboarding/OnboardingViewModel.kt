package com.pawsup.android.ui.screens.onboarding

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawsup.android.data.repository.SettingsRepository
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    @ApplicationContext private val app: Context,
    private val settingsRepository: SettingsRepository,
    private val permissionHelper: PermissionHelper,
) : ViewModel() {

    private val _step = MutableStateFlow(0)
    val step: StateFlow<Int> = _step.asStateFlow()

    private val _usageGranted = MutableStateFlow(permissionHelper.hasUsageStatsPermission())
    val usageGranted = _usageGranted.asStateFlow()

    private val _overlayGranted = MutableStateFlow(permissionHelper.hasOverlayPermission())
    val overlayGranted = _overlayGranted.asStateFlow()

    private val _notifGranted = MutableStateFlow(permissionHelper.hasPostNotificationsPermission())
    val notificationsGranted = _notifGranted.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                _usageGranted.value = permissionHelper.hasUsageStatsPermission()
                _overlayGranted.value = permissionHelper.hasOverlayPermission()
                _notifGranted.value = permissionHelper.hasPostNotificationsPermission()
                delay(600)
            }
        }
    }

    fun next() {
        _step.value = (_step.value + 1).coerceAtMost(3)
    }

    fun openUsageSettings() = permissionHelper.openUsageAccessSettings()

    fun openOverlaySettings() = permissionHelper.openOverlaySettings()

    fun requestNotificationPermission() {
        val intent = Intent().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                putExtra(Settings.EXTRA_APP_PACKAGE, app.packageName)
            } else {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.parse("package:${app.packageName}")
            }
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        app.startActivity(intent)
    }

    fun finishOnboarding(onBattery: () -> Unit) {
        viewModelScope.launch {
            settingsRepository.setServiceEnabled(true)
            settingsRepository.setOnboardingComplete()
            UsageMonitorService.start(app)
            UsageWatchdogScheduler.schedule(app)

            val prompted = settingsRepository.oemBatteryPrompted.first()
            if (!prompted && !permissionHelper.isIgnoringBatteryOptimizations()) {
                onBattery()
                settingsRepository.setOemBatteryPrompted()
            }
        }
    }
}
