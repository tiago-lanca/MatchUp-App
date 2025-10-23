package com.app.matchup.ui.components.Events

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.app.matchup.CreateEventActivity
import com.app.matchup.models.Event
import com.app.matchup.samples.EventSamples
import com.app.matchup.ui.components.FloatingButtonsMainScreen
import com.app.matchup.ui.components.MapScreen
import com.app.matchup.ui.theme.EVENT_BACKGROUND_COLOR
import com.app.matchup.utilities.Tools
import com.app.matchup.utilities.Tools.navigateTo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import kotlin.times

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    eventList: List<Event>
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val numberEvents = eventList.size
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var cameraPositionState = rememberCameraPositionState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB0BEC5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth()
        ) {
            MapScreen(cameraPositionState)
        }

        if (selectedEvent == null) {
            // Near Events Section
            EventList(
                modifier = Modifier.align(Alignment.BottomCenter),
                eventList = eventList,
                onClickEventItem = { event ->
                    event.address?.let { address ->
                        Tools.moveCameraTo(
                            latLng = LatLng(address.latitude!!, address.longitude!!),
                            coroutineScope = coroutineScope,
                            cameraPositionState = cameraPositionState
                        )
                    }
                    selectedEvent = event
                }
            )

            // Floating Buttons on Event List (no event selected)

            FloatingButtonsMainScreen(
                onMyLocationButtonClick = {
                    Tools.moveCameraTo(Tools.SeixalCoords, Tools.defaultZoom, coroutineScope, cameraPositionState )
                },
                onCreateNewEventButtonClick = {
                    //Log.i("TEST", "Button create event clicked.")
                    (context as Activity).navigateTo(CreateEventActivity::class.java)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = screenHeight * 0.40f)
            )
        }

        // Has event selected
        selectedEvent?.let { event ->

            EventDetails(
                event,
                onClose = { selectedEvent = null }
            )

            // Floating Buttons
            FloatingButtonsMainScreen(
                onMyLocationButtonClick = {
                    Tools.moveCameraTo(Tools.SeixalCoords, Tools.defaultZoom, coroutineScope, cameraPositionState )
                },
                onCreateNewEventButtonClick = {
                    //Log.i("TEST", "Button create event clicked.")
                    (context as Activity).navigateTo(CreateEventActivity::class.java)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = screenHeight * 0.46f)
            )
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventListPreview() {
    MainScreen(EventSamples.createSampleListEvents())
}