package com.app.matchup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.matchup.R
import com.app.matchup.ui.theme.SIGNIN_BUTTON_COLOR
import com.app.matchup.utilities.Tools

@Composable
fun ProfilePictureWithEdit(
    imageUrl: String,
    onEditClick: () -> Unit
){
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        AsyncImage(
            model = Tools.base64ToBitmap(imageUrl),
            contentDescription = stringResource(R.string.profile_picture_image_desc),
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(SIGNIN_BUTTON_COLOR)
                .clickable{ onEditClick() },
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.edit_icon_desc),
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}