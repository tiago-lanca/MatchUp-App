package com.app.matchup

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import com.app.matchup.models.Event
import com.app.matchup.samples.EventSamples
import com.app.matchup.services.EventService
import com.app.matchup.ui.components.Events.CreateEventScreen
import com.app.matchup.ui.components.Events.MainScreen
import com.app.matchup.ui.components.Login.LoginScreen
import com.app.matchup.ui.theme.MatchUpTheme
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            MatchUpTheme {

                val serverRoot = "http://10.0.2.2:8081"
                var eventList = mutableListOf<Event>()

                EventService().GetEvents {
                    eventList = it as MutableList<Event>
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