package com.pawsup.break_experience.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun PetInteractionZone(
    onPet: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    var triggered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (triggered) 1.02f else 1.0f,
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        finishedListener = { triggered = false },
        label = "pet_scale"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    triggered = true
                    onPet()
                })
            }
    ) {
        content()
    }
}
