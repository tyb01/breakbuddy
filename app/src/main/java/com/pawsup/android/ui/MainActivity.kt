package com.pawsup.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pawsup.android.data.repository.SettingsRepository
import com.pawsup.android.service.ensureMonitoringServiceRunning
import com.pawsup.android.ui.navigation.AppNavigation
import com.pawsup.android.ui.theme.PawsUpTheme
import com.pawsup.android.util.PermissionHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var permissionHelper: PermissionHelper

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (isActive) {
                    ensureMonitoringServiceRunning(
                        applicationContext,
                        settingsRepository,
                        permissionHelper,
                    )
                    delay(2_000)
                }
            }
        }
        setContent {
            val prefs = settingsRepository.preferences.collectAsState(initial = null)
            PawsUpTheme(themeMode = prefs.value?.themeMode ?: com.pawsup.android.data.datastore.ThemeMode.SYSTEM) {
                AppNavigation(
                    onRequestBatteryExemption = { permissionHelper.requestIgnoreBatteryOptimizations() },
                )
            }
        }
    }
}
