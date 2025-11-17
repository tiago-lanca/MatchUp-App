package com.app.matchup.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.matchup.extensions.getSportIconSize

@Composable
fun FilterTag(
    text: String? = null,
    icon: Int? = null,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    onRemoveFilterClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
            .animateContentSize()
    ){
        if(text != null) {
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
        if(icon != null){
            Icon(
                painter = painterResource(icon),
                contentDescription = "Sport icon filter",
                tint = Color.Unspecified,
                modifier = Modifier.size(25.dp)
            )
        }

        Spacer(Modifier.size(5.dp))
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove filter",
            tint = Color.Red,
            modifier = Modifier
                .size(18.dp)
                .clickable { onRemoveFilterClick() }
        )
    }
}