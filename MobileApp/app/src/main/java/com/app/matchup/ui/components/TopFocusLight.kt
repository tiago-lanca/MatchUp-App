package com.app.matchup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopFocusLight(){

    // Little light above the logo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(width = 300.dp, height = 250.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFB0B0B0).copy(alpha = 0.12f),
                        Color.Transparent
                    ),
                    start = Offset(300f, 0f),
                    end = Offset(0f, 600f)
                )
            )
    )
}