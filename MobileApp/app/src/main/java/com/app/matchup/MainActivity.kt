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
import com.app.matchup.models.Event
import com.app.matchup.ui.components.Events.MainScreen
import com.app.matchup.ui.theme.MatchUpTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MatchUpTheme {

                val context = LocalContext.current

                // Checks if there's any new event created from the CreateEventActivity
                val eventCreated = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("createdEvent", Event::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra<Event>("createdEvent")
                }

                /*LaunchedEffect(eventCreated) {
                    eventCreated?.copy(admin = EventService.getEventAdmin(eventCreated.id))
                }*/

                MainScreen(context, event = eventCreated)


                //val eventList = EventSamples.createSampleListEvents()
                //CreateEventScreen()
                //RegisterScreen()
                //LoginScreen()
                //MapScreen()

                /*SelectLocationScreen(
                    onLocationSelected = { positionSelected ->
                        print("Location selected: ${positionSelected.latitude}, ${positionSelected.longitude}")
                    }
                )*/

                //MainMenuScreen()
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