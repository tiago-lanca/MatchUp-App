package com.app.matchup

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.matchup.models.Event
import com.app.matchup.ui.SplashScreen
import com.app.matchup.ui.components.Events.MainScreen
import com.app.matchup.ui.theme.MatchUpTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MatchUpTheme {

                /*val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "splashScreen"
                ){
                    composable("splashScreen") {
                        SplashScreen(
                            onFinish = {
                                navController.navigate("mainScreen") {
                                    popUpTo("splashScreen") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("mainScreen"){

                    }
                }*/

                val context = LocalContext.current

                // Checks if there's any new event created from the CreateEventActivity
                val eventCreated = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("createdEvent", Event::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra<Event>("createdEvent")
                }

                MainScreen(context, event = eventCreated)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MatchUpTheme {
        MainScreen(LocalContext.current)
    }
}