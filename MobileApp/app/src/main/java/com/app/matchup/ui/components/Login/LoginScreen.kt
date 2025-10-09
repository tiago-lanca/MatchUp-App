package com.app.matchup.ui.components.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R

@Composable
fun LoginScreen() {
    Scaffold(
        containerColor = Color(0xFF0D0D1B),

        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF0D0D1B),
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
                        text = "Sign in to your\naccount",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                LoginForm()

                Spacer(modifier = Modifier.height(50.dp))

                // Sign In Button
                Button(
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF0B7422),
                        disabledContentColor = Color(0xFF0B7422),
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
                    Text("Sign In")
                }

                // Link to Sign Up account
                Row(
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text(
                        text ="Don’t have an account? ",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Text(text = "Sign Up",
                        color = Color(0xFF1565C0),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPanelPreview(){
    LoginScreen()
}
