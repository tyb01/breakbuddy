package com.pawsup.break_experience.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawsup.ui.theme.CrimsonTextFamily

@Composable
fun BreakTimerDisplay(
    remainingSeconds: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = formatTime(remainingSeconds),
        modifier = modifier.padding(horizontal = 24.dp),
        fontFamily = CrimsonTextFamily,
        fontSize = 48.sp,
        color = Color(0xFFF5ECD7).copy(alpha = 0.8f),
        lineHeight = 52.sp
    )
}

private fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%d:%02d".format(m, s)
}
