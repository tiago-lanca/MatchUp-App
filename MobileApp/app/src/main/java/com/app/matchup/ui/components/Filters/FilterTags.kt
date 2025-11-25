package com.app.matchup.ui.components.Filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FilterTag(
    text: String? = null,
    icon: Int? = null,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    onRemoveFilterClick: () -> Unit
){
        Box(
            modifier = Modifier

                .graphicsLayer {
                    translationY = 6f
                    shadowElevation = 20f
                    shape = RoundedCornerShape(14.dp)
                    clip = false
                }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .graphicsLayer {
                    shadowElevation = 18f
                    shape = RoundedCornerShape(14.dp)
                    clip = false
                    translationY = -5f
                }
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            if (text != null) {
                Text(
                    text = text,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }
            if (icon != null) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "Sport icon filter",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(25.dp)
                )
            }

            Spacer(Modifier.size(5.dp))
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(Color.White.copy(alpha = 0.15f), shape = CircleShape)
                    .clickable { onRemoveFilterClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove filter",
                    tint = Color.White,
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            color = Color(0xAA000000),
                            shape = CircleShape
                        )
                        .padding(3.dp)
                )
            }
        }

}