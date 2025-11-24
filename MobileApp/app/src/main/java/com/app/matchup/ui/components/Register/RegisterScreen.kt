package com.app.matchup.ui.components.Register

import android.app.Activity
import android.content.Context
import android.text.style.UnderlineSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.R
import com.app.matchup.ui.components.LightFromAbove
import com.app.matchup.ui.components.Login.LoginForm
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.ui.theme.REGISTER_BUTTON_COLOR
import com.app.matchup.ui.theme.SIGNIN_BUTTON_COLOR
import com.app.matchup.viewmodels.RegisterAccountViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    context: Context,
    registerVM: RegisterAccountViewModel = viewModel()
) {

    val user by registerVM.user.collectAsState()
    val validationState by registerVM.validationState.collectAsState()
    val confirmPassword by registerVM.confirmPasswordState.collectAsState()



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
                            .clickable{
                                (context as Activity).finish()
                            }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BACKGROUND_COLOR
                ),
                title = {
                    Image(
                        painter = painterResource(R.drawable.matchup_white),
                        contentDescription = stringResource(R.string.app_logo_desc),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(80.dp)
                    )
                }
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
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
            ) {

                // Title
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = stringResource(R.string.register_label),
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
                                append(stringResource(R.string.already_have_account_label))
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4D81E7),
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append(stringResource(R.string.log_in_label))
                            }
                        },
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                RegisterForm(
                    user,
                    onNameChanged = registerVM::onNameChanged,
                    onEmailChanged = registerVM::onEmailChanged,
                    onCountryChanged = registerVM::onCountryChanged,
                    onCityChanged = registerVM::onCityChanged,
                    onMobilePhoneChanged = registerVM::onMobilePhoneChanged,
                    onPasswordChanged = registerVM::onPasswordChanged,
                    onConfirmPasswordChanged = registerVM::onConfirmPasswordChanged,
                    confirmPassword = confirmPassword,
                    onGenderChanged = registerVM::onGenderChanged,
                    onSportChanged = registerVM::onFavoriteSportChanged,
                    validationState
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Register Button
                Button(
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = REGISTER_BUTTON_COLOR,
                        disabledContentColor = REGISTER_BUTTON_COLOR,
                        disabledContainerColor = Color.White
                    ),
                    onClick = { registerVM.onRegisterNewAccount(context){ success ->

                    } },
                    modifier = Modifier
                        .width(250.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(R.string.register_label))
                }
            }
        }
    }
    // Little light above the logo
    LightFromAbove()
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    //RegisterScreen()
}