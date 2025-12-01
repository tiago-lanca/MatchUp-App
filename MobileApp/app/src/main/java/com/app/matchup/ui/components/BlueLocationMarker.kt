package com.app.matchup.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BlueLocationMarker(
    size: Dp = 22.dp,
    pulseSize: Dp = 48.dp,
    color: Color = Color(0xFF4285F4)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(pulseSize)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    alpha = 0.35f
                }
                .background(color.copy(alpha = 0.4f), shape = CircleShape)
        )

        Box(
            modifier = Modifier
                .size(size)
                .background(color, CircleShape)
                .border(2.dp, Color.White, CircleShape)
        )
    }
}