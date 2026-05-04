package com.pawsup.android.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pawsup.android.data.repository.SettingsRepository
import com.pawsup.android.R
import com.pawsup.android.ui.screens.customize.CustomizeScreen
import com.pawsup.android.ui.screens.home.MainScreen
import com.pawsup.android.ui.screens.onboarding.OnboardingFlow
import com.pawsup.android.ui.screens.reliability.ReliabilityScreen
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Composable
fun AppNavigation(
    onRequestBatteryExemption: () -> Unit,
) {
    val context = LocalContext.current
    val settingsRepository = androidx.compose.runtime.remember {
        EntryPointAccessors.fromApplication(
            context.applicationContext,
            SettingsRepositoryEntryPoint::class.java,
        ).settingsRepository()
    }

    val prefs by produceState<com.pawsup.android.data.datastore.UserPreferences?>(
        initialValue = null,
    ) {
        settingsRepository.preferences.collect { value = it }
    }

    if (prefs == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (prefs?.onboardingComplete != true) {
        OnboardingFlow(onRequestBatteryExemption = onRequestBatteryExemption)
        return
    }

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    val items = listOf(
        BottomNavItem(Routes.HOME, R.string.nav_home, Icons.Default.Home),
        BottomNavItem(Routes.RELIABILITY, R.string.nav_reliability, Icons.Default.Shield),
        BottomNavItem(Routes.CUSTOMIZE, R.string.nav_customize, Icons.Default.Tune),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    val selected = currentRoute == item.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navController.navigate(item.route) {
                                    popUpTo(Routes.HOME) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { androidx.compose.material3.Icon(item.icon, contentDescription = null) },
                        label = { androidx.compose.material3.Text(stringResource(item.labelRes)) },
                    )
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding),
        ) {
            composable(Routes.HOME) {
                MainScreen()
            }
            composable(Routes.RELIABILITY) {
                ReliabilityScreen(onBack = { navController.popBackStack() })
            }
            composable(Routes.CUSTOMIZE) {
                CustomizeScreen()
            }
        }
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SettingsRepositoryEntryPoint {
    fun settingsRepository(): SettingsRepository
}

private object Routes {
    const val HOME = "home"
    const val RELIABILITY = "reliability"
    const val CUSTOMIZE = "customize"
}

private data class BottomNavItem(
    val route: String,
    val labelRes: Int,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
)
