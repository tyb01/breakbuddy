package com.pawsup.android.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.pawsup.android.data.datastore.ThemeMode

private val LightScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = CharcoalDark,
    secondary = OrangePrimary,
    background = CreamBackground,
    surface = CreamBackground,
    onBackground = CharcoalDark,
    onSurface = CharcoalDark,
)

private val DarkScheme = darkColorScheme(
    primary = OrangePrimary,
    onPrimary = CharcoalDark,
    secondary = OrangePrimary,
    background = CharcoalDark,
    surface = CharcoalDark,
    onBackground = CreamBackground,
    onSurface = CreamBackground,
)

@Composable
fun PawsUpTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit,
) {
    val dark = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val scheme = if (dark) DarkScheme else LightScheme
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = scheme.primary.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !dark
    }
    MaterialTheme(
        colorScheme = scheme,
        typography = PawsUpTypography,
        content = content,
    )
}
