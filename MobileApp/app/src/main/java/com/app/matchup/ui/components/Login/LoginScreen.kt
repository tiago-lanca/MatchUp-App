package com.app.matchup.ui.components.Login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.app.matchup.RegisterActivity
import com.app.matchup.models.User
import com.app.matchup.ui.components.LightFromAbove
import com.app.matchup.ui.components.SnackbarMessage
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.ui.theme.SIGNIN_BUTTON_COLOR
import com.app.matchup.utilities.Tools.navigateTo
import com.app.matchup.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    context: Context,
    loginVM: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    userCreated: User? = null
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val email by loginVM.email.collectAsState()
    val password by loginVM.password.collectAsState()
    val isLoading by loginVM.isLoading.collectAsState()
    val error by loginVM.error.collectAsState()
    val loginSuccess by loginVM.loginSuccess.collectAsState()

    if(loginSuccess){
        onLoginSuccess()
    }

    LaunchedEffect(Unit) {

        if(userCreated != null){
            loginVM.onEmailChanged(userCreated.email)

            scope.launch {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.register_account_success_message)
                )
            }
        }
    }

    Scaffold(
        containerColor = BACKGROUND_COLOR,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.go_back_button_desc),
                        tint = Color.White,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable { (context as Activity).finish() }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BACKGROUND_COLOR
                ),
                title = {}
            )
        },
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
                Spacer(modifier = Modifier.height(20.dp))

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
                    onEmailChanged = { loginVM.onEmailChanged(it) },
                    onPasswordChanged = { loginVM.onPasswordChanged(it) }
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
                    onClick = { loginVM.onLoginClicked(context) },
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
                    Text(
                        text = stringResource(R.string.signup_text_2),
                        color = Color(0xFF1565C0),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {
                                (context as Activity).navigateTo(RegisterActivity::class.java)
                            }
                    )
                }
            }
        }
        SnackbarMessage(snackbarHostState)
    }
    // Little light above the logo
    LightFromAbove()
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LoginPanelPreview(){
    LoginScreen(context = LocalContext.current,
        onLoginSuccess = {},
        loginVM = LoginViewModel()
    )
}
