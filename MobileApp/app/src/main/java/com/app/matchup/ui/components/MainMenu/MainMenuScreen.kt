package com.app.matchup.ui.components.MainMenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.ReportGmailerrorred
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R
import com.app.matchup.ui.components.TopFocusLight
import com.app.matchup.ui.theme.BACKGROUND_COLOR

@Composable
fun MainMenuScreen() {
    Scaffold(
        containerColor = BACKGROUND_COLOR,

        bottomBar = {
            BottomAppBar(
                containerColor = BACKGROUND_COLOR,
                contentColor = Color.Gray,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ReportGmailerrorred,
                            contentDescription = "Report/Feedback Icon",
                            tint = Color.Red,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Report / Feedback Problem",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "MatchUp - v.1.0.0",
                    )
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                // Logo and Title
                Spacer(modifier = Modifier.height(10.dp))

                // Logo
                Image(
                    painter = painterResource(R.drawable.matchup_white),
                    contentDescription = "MatchUp Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(80.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Logged user profile pic. and name/email
                UserProfileSection()

                Spacer(modifier = Modifier.height(30.dp))

                // Menu items
                MenuItems(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

    // Little light above the logo
    TopFocusLight()
}

@Preview
@Composable
fun MainMenuScreenPreview() {
    MainMenuScreen()
}


