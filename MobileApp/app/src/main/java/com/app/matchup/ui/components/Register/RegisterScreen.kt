package com.app.matchup.ui.components.Register

import android.text.style.UnderlineSpan
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R
import com.app.matchup.ui.components.Login.LoginForm
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.ui.theme.REGISTER_BUTTON_COLOR
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
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
            ) {

                // Logo and Title
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {

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

                    Text(
                        text = "Register",
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            ) {
                                append("Already have an account?  ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4D81E7),
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("Log in")
                            }
                        },
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                RegisterForm()

                Spacer(modifier = Modifier.height(20.dp))

                // Register Button
                Button(
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = REGISTER_BUTTON_COLOR,
                        disabledContentColor = REGISTER_BUTTON_COLOR,
                        disabledContainerColor = Color.White
                    ),
                    onClick = { TODO() },
                    modifier = Modifier
                        .width(250.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(8.dp)
                ) {
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