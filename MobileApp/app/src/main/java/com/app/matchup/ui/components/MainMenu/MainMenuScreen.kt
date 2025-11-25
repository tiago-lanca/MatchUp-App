package com.app.matchup.ui.components.MainMenu

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReportGmailerrorred
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.ui.components.Login.LoginActivity
import com.app.matchup.MainActivity
import com.app.matchup.MyEventsActivity
import com.app.matchup.ProfileActivity
import com.app.matchup.R
import com.app.matchup.RegisterActivity
import com.app.matchup.SelectLocationActivity
import com.app.matchup.models.User
import com.app.matchup.ui.components.TopFocusLight
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.utilities.Tools.navigateTo
import com.app.matchup.services.UserSession
import com.app.matchup.ui.components.LogoutBottomSheet
import com.app.matchup.ui.components.Register.RegisterScreen
import com.app.matchup.ui.components.SnackbarMessage
import kotlinx.coroutines.launch

@Composable
fun MainMenuScreen(

) {
    val context = LocalContext.current
    var user by remember { mutableStateOf<User?>(null) }
    var showLogoutBottomSheet by remember {mutableStateOf(false)}



    LaunchedEffect(Unit) {
        user = UserSession.getUser(context)
    }

    Scaffold(
        containerColor = BACKGROUND_COLOR,
        bottomBar = {
            BottomAppBar(
                containerColor = BACKGROUND_COLOR,
                contentColor = Color.Gray,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .height(100.dp)
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
                            contentDescription = stringResource(R.string.report_feedback_icon_desc),
                            tint = Color.Red,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = stringResource(R.string.report_feedback_label),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.app_name_and_version),
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

                if(user != null) {
                    user?.let {
                        UserProfileSection(
                            user = user!!,
                            onProfileClick = {
                                val intent = Intent(context, ProfileActivity::class.java)
                                intent.putExtra("current_user", user)
                                context.startActivity(intent)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Menu items
                MenuItems(
                    modifier = Modifier.padding(innerPadding),
                    isUserLoggedIn = user != null,
                    onLoginClick = { (context as Activity).navigateTo(activity = LoginActivity::class.java) },
                    onRegisterClick = { (context as Activity).navigateTo(activity = RegisterActivity::class.java) },
                    onHomeClick = { (context as Activity).navigateTo(activity = MainActivity::class.java) },
                    onMyEventsClick = {
                        val intent = Intent(context, MyEventsActivity::class.java)
                        intent.putExtra("current_user", user)
                        context.startActivity(intent)
                    },
                    onSearchEventsClick = { (context as Activity).navigateTo(activity = MainActivity::class.java) },
                    onCreateNewEventClick = { (context as Activity).navigateTo(activity = SelectLocationActivity::class.java) },
                    onProfileClick = {
                        {
                            val intent = Intent(context, ProfileActivity::class.java)
                            intent.putExtra("current_user", user)
                            context.startActivity(intent)
                        }
                    },
                    onSignOutClick = {
                        showLogoutBottomSheet = true
                    }
                )
            }
        }
    }

    // Little light above the logo
    TopFocusLight()

    if(showLogoutBottomSheet){
        LogoutBottomSheet(
            onDismiss = { showLogoutBottomSheet = false },
            onLogout = {
                UserSession.logoutUser(context)
                (context as Activity).navigateTo(activity = LoginActivity::class.java, closeCurrentActivity = true)
            }
        )
    }
}

@Preview
@Composable
fun MainMenuScreenPreview() {
    MainMenuScreen()
}


