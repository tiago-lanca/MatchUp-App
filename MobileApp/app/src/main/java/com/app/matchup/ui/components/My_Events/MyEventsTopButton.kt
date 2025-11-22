package com.app.matchup.ui.components.My_Events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.matchup.R
import com.app.matchup.ui.theme.GENDER_MALE_COLOR

@Composable
fun MyEventsTopButton(
    text: String,
    isSelected: Boolean,
    icon: ImageVector,
    iconTint: Color = Color.Black,
    iconSize: Dp = 20.dp,
    contentDescription: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = { onButtonClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isSelected) GENDER_MALE_COLOR else Color.White,
            contentColor = Color.Black
        ),
        modifier = modifier.height(32.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp),
        

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint,
                modifier = modifier.size(iconSize)
                )
            Spacer(modifier = modifier.size(5.dp))
            Text(
                text = text
            )
            Spacer(modifier = modifier.size(15.dp))
        }
    }
}


@Preview
@Composable
fun MyEventsTopButtonPreview(){
    MyEventsTopButton(
        text = "Active",
        isSelected = true,
        icon = Icons.Default.Check,
        iconTint = Color.White,
        iconSize = 20.dp,
        contentDescription = stringResource(R.string.check_icon_desc),
        onButtonClick = { },
        modifier = Modifier
    )
}