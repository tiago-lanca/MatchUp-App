package com.app.matchup

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.app.matchup.models.Event
import com.app.matchup.samples.EventSamples
import com.app.matchup.services.EventService
import com.app.matchup.ui.components.Events.MainScreen
import com.app.matchup.ui.theme.MatchUpTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            MatchUpTheme {

                val serverRoot = "http://10.0.2.2:8081"
                var eventList by remember { mutableStateOf(emptyList<Event>()) }
                val _eventService = remember { EventService() }

                LaunchedEffect(Unit) {
                    eventList = _eventService.getEvents()
                }


                //val eventList = EventSamples.createSampleListEvents()
                MainScreen(eventList = eventList)
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
        MainScreen(EventSamples.createSampleListEvents())
    }
}