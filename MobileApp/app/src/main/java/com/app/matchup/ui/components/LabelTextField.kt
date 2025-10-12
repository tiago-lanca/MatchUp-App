package com.app.matchup.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabelTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    labelColor: Color,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
){
    Column (verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = label,
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
            color = labelColor
        )
        TextField(
            value = value,
            onValueChange = onValueChanged,
            singleLine = singleLine,
            enabled = enabled,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}