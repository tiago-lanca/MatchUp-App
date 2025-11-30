package com.app.matchup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.matchup.ui.components.MainMenu.MainMenuScreen
import com.app.matchup.ui.theme.MatchUpTheme

class MainMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatchUpTheme {

                MainMenuScreen()
            }
        }
    }
}


@Preview
@Composable
fun MainMenuActivityPreview(){
    MatchUpTheme {
        MainMenuScreen()
    }
}