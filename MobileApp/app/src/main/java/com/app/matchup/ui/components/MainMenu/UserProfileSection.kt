package com.app.matchup.ui.components.MainMenu

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R
import com.app.matchup.models.User
import java.util.UUID

@Composable
fun UserProfileSection(){
    val user = User(
        id = UUID.randomUUID(),
        name = "Tiago Lança",
        email = "tiagotestest@email.com",
        passwordHash = "1234",
        profilePicture = R.drawable.profile_picture.toString()
    )

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { }
            )
    ) {
        Image(
            painter = painterResource(user.profilePicture!!.toInt()),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
        )

        Column (
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = user.name,
                color = Color.White,
                fontSize = 20.sp
            )

            Text(
                text = user.email,
                color = Color.LightGray,
                fontSize = 15.sp
            )
        }

        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = "Go To Profile Icon",
            tint = Color.White,
            modifier = Modifier
                .size(40.dp)
        )

    }
}

@Preview
@Composable
fun UserProfileSectionPreview(){
    UserProfileSection()
}