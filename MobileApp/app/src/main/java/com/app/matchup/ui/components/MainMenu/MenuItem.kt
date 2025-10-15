package com.app.matchup.ui.components.MainMenu

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuItem(icon: ImageVector, iconDescription: String, text: String){
    TextButton(
        onClick = { }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            tint = Color.White,
            modifier = Modifier
                .padding(end = 8.dp)
        )
        Text(
            text = text,
            color = Color.White,
            fontSize = 23.sp
        )
    }
}

@Preview
@Composable
fun MenuItemPreview(){
    MenuItem(
        icon = Icons.Default.Home,
        iconDescription = "Home Icon",
        text = "Home"
    )
}