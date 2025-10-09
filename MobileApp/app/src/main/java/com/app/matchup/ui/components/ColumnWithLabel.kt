package com.app.matchup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R

@Composable
fun ColumnWithLabel(label: String,
                    imageIcon: String = "",
                    text: String,
                    textColor: Color = Color.White,
                    textFontSize: Int = 18,
                    textFontWeight: FontWeight = FontWeight.Normal
) {
    Column (
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            color = Color.Gray
        )
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(imageIcon != "") {
                Image(
                    painter = painterResource(R.drawable.football_icon),
                    contentDescription = "Football Icon",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(top = 5.dp)
                )
            }
            Text(
                text = text,
                color = textColor,
                fontSize = textFontSize.sp,
                fontWeight = textFontWeight,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}