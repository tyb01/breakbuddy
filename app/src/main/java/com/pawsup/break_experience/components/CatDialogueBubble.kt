package com.pawsup.break_experience.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawsup.ui.theme.CrimsonTextItalicFamily

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CatDialogueBubble(
    line: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = line,
            transitionSpec = {
                fadeIn(animationSpec = tween(600, delayMillis = 200)) togetherWith
                    fadeOut(animationSpec = tween(600))
            }
        ) { text ->
            Text(
                text = text,
                fontFamily = CrimsonTextItalicFamily,
                fontSize = 22.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )
        }
    }
}
