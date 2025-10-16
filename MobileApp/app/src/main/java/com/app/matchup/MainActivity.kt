package com.app.matchup

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.matchup.samples.EventSamples
import com.app.matchup.ui.components.SelectLocationScreen
import com.app.matchup.ui.components.Events.EventList
import com.app.matchup.ui.theme.MatchUpTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatchUpTheme {
                //val eventList = EventSamples.createSampleListEvents()
                //EventList(EventSamples.createSampleListEvents())
                //CreateEventScreen()
                //RegisterScreen()

                //MapScreen()

                SelectLocationScreen(
                    onLocationSelected = { positionSelected ->
                        print("Location selected: ${positionSelected.latitude}, ${positionSelected.longitude}")
                    }
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MatchUpTheme {
        EventList(EventSamples.createSampleListEvents())
    }
}