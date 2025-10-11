package com.app.matchup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.matchup.ui.components.Login.LoginScreen
import com.app.matchup.ui.theme.MatchUpTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatchUpTheme {
                LoginScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginActivityPreview() {
    MatchUpTheme {
        LoginScreen()
    }
}