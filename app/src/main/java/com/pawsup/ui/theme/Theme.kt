package com.pawsup.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.pawsup.R

val CrimsonTextFamily = FontFamily(
    Font(R.font.crimsontext_regular, FontWeight.Normal),
    Font(R.font.crimsontext_bold, FontWeight.Bold),
    Font(R.font.crimsontext_semibold, FontWeight.SemiBold)
)

val CrimsonTextItalicFamily = FontFamily(
    Font(R.font.crimsontext_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.crimsontext_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.crimsontext_semibolditalic, FontWeight.SemiBold, FontStyle.Italic)
)

private val DarkColorScheme = darkColorScheme(
    primary         = Color(0xFFE8A87C),
    onPrimary       = Color(0xFF1A0C00),
    secondary       = Color(0xFFF5ECD7),
    onSecondary     = Color(0xFF1A0C00),
    background      = Color(0xFF0F0A08),
    onBackground    = Color(0xFFF5ECD7),
    surface         = Color(0xFF2A1F1A),
    onSurface       = Color(0xFFF5ECD7),
    surfaceVariant  = Color(0xFF3D2E26),
    onSurfaceVariant= Color(0xFFCCB89A),
)

@Composable
fun PawsUpTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography(),
        content = content
    )
}
