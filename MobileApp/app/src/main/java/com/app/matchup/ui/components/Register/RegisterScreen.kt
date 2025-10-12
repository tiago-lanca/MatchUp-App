package com.app.matchup.ui.components.Register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R
import com.app.matchup.ui.components.Login.LoginForm
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.ui.theme.SIGNIN_BUTTON_COLOR


@Composable
fun RegisterScreen() {
    Scaffold(
        containerColor = BACKGROUND_COLOR,

        bottomBar = {
            BottomAppBar(
                containerColor = BACKGROUND_COLOR,
                contentColor = Color.Gray,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .height(25.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "MatchUp - v.1.0.0",
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(70.dp))

                // Logo and Title
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {

                    // Logo
                    Image(
                        painter = painterResource(R.drawable.matchup_white),
                        contentDescription = "MatchUp Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(bottom = 20.dp)
                    )

                    Text(
                        text = "Register",
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )

                    Text(
                        text = "Already have an account? Log in",
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                RegisterForm()

                Spacer(modifier = Modifier.height(50.dp))

                // Sign In Button
                Button(
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = SIGNIN_BUTTON_COLOR,
                        disabledContentColor = SIGNIN_BUTTON_COLOR,
                        disabledContainerColor = Color.White
                    ),
                    onClick = { TODO() },
                    modifier = Modifier
                        .width(200.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Login,
                        contentDescription = "Login Icon",
                        modifier = Modifier
                            .padding(end = 5.dp)
                    )
                    Text("Register")
                }
            }
        }

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
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    RegisterScreen()
}