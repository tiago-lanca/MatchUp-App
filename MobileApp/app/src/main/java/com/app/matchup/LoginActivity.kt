package com.app.matchup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.app.matchup.models.User
import com.app.matchup.ui.components.Login.LoginScreen
import com.app.matchup.ui.theme.MatchUpTheme
import com.app.matchup.utilities.UserSession

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val existingUser = UserSession.getUser(this)
        if(existingUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            MatchUpTheme {
                val context = LocalContext.current

                LoginScreen(
                    context,
                    onLoginSuccess = {
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            if(context is Activity) context.finish()

                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginActivityPreview() {
    MatchUpTheme {
        LoginScreen(context = LocalContext.current, onLoginSuccess = {})
    }
}