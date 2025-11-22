package com.app.matchup.ui.components.Login

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.R
import com.app.matchup.ui.components.LightFromAbove
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.ui.theme.SIGNIN_BUTTON_COLOR
import com.app.matchup.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    context: Context,
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()

    if(loginSuccess){
        onLoginSuccess()
    }

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
                    text = stringResource(R.string.app_name_and_version),
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
                        contentDescription = stringResource(R.string.app_logo_desc),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(bottom = 20.dp)
                    )

                    Text(
                        text = stringResource(R.string.signin_title_text),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                LoginForm(
                    email = email,
                    password = password,
                    onEmailChanged = { viewModel.onEmailChanged(it) },
                    onPasswordChanged = { viewModel.onPasswordChanged(it) }
                )
                if(error != null){
                    Text(
                        text = error ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))

                // Sign In Button
                Button(
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = SIGNIN_BUTTON_COLOR,
                        disabledContentColor = SIGNIN_BUTTON_COLOR,
                        disabledContainerColor = Color.White
                    ),
                    onClick = { viewModel.onLoginClicked(context) },
                    enabled = !isLoading,
                    modifier = Modifier
                        .width(200.dp)
                ) {
                    if(isLoading){
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp).padding(end = 5.dp)
                        )
                    }
                    else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Login,
                            contentDescription = stringResource(R.string.login_icon_desc),
                            modifier = Modifier
                                .padding(end = 5.dp)
                        )
                    }
                    Text(stringResource(R.string.signin_label))
                }

                // Link to Sign Up account
                Row(
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    // "Don’t have an account?"
                    Text(
                        text = stringResource(R.string.signup_text_1),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    // "Sign Up"
                    Text(text = stringResource(R.string.signup_text_2),
                        color = Color(0xFF1565C0),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                }
            }
        }

        // Little light above the logo
        LightFromAbove()
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LoginPanelPreview(){
    LoginScreen(context = LocalContext.current,
        onLoginSuccess = {},
        viewModel = LoginViewModel()
    )
}
