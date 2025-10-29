package com.app.matchup.ui.components.Events

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import com.app.matchup.extensions.toMapDisplay
import com.app.matchup.models.Event
import com.app.matchup.samples.EventSamples
import com.app.matchup.ui.components.FloatingButtonsMainScreen
import com.app.matchup.ui.components.MapScreen
import com.app.matchup.ui.theme.EVENT_BACKGROUND_COLOR
import com.app.matchup.utilities.AppConstants
import com.app.matchup.utilities.AppConstants.MAP_DISPLAY_OFFSET
import com.app.matchup.utilities.AppConstants.SeixalCoords
import com.app.matchup.utilities.Tools
import com.app.matchup.utilities.Tools.navigateTo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import kotlin.times

@OptIn(ExperimentalMaterial3Api::class)
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
    val cameraPositionState = rememberCameraPositionState()

    val scaffoldState = rememberBottomSheetScaffoldState()

    val sheetState = scaffoldState.bottomSheetState

    val fabPadding by animateDpAsState(
        when (sheetState.currentValue){
            SheetValue.Expanded -> 240.dp
            SheetValue.PartiallyExpanded -> 40.dp
            else -> 180.dp
        }
    )

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFB0BEC5))
        ) {

            // Near Events Section
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = 180.dp,
                sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                sheetContainerColor = EVENT_BACKGROUND_COLOR,
                sheetDragHandle = { BottomSheetDefaults.DragHandle() },
                sheetContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 180.dp, max = 410.dp)
                            .background(
                                color = EVENT_BACKGROUND_COLOR,
                                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                            )
                    ) {

                        if (selectedEvent == null) {
                            EventList(
                                eventList = eventList,
                                onClickEventItem = { event ->
                                    event.address?.let { address ->
                                        Tools.moveCameraTo(
                                            latLng = LatLng(
                                                address.latitude!! - MAP_DISPLAY_OFFSET,
                                                address.longitude!!
                                            ),
                                            coroutineScope = coroutineScope,
                                            cameraPositionState = cameraPositionState
                                        )
                                    }
                                    selectedEvent = event
                                }
                            )
                        } else {
                            EventDetails(
                                event = selectedEvent!!,
                                onClose = { selectedEvent = null },
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    MapScreen(
                        eventList,
                        cameraPositionState,
                        onMarkerClick = { event ->
                            selectedEvent = event

                            Tools.moveCameraTo(
                                LatLng(
                                    event.address?.latitude!! - MAP_DISPLAY_OFFSET,
                                    event.address?.longitude!!
                                ),
                                AppConstants.defaultZoom,
                                coroutineScope,
                                cameraPositionState
                            )
                        }
                    )
                    FloatingButtonsMainScreen(
                        onMyLocationButtonClick = {
                            Tools.moveCameraTo(
                                SeixalCoords.toMapDisplay(),
                                AppConstants.defaultZoom,
                                coroutineScope,
                                cameraPositionState
                            )
                        },
                        onCreateNewEventButtonClick = {
                            //Log.i("TEST", "Button create event clicked.")
                            (context as Activity).navigateTo(CreateEventActivity::class.java)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 10.dp, bottom = fabPadding)
                    )
                }
            }

            // Floating Buttons on Event List (no event selected)

            /*FloatingButtonsMainScreen(
                onMyLocationButtonClick = {
                    Tools.moveCameraTo(
                        SeixalCoords.toMapDisplay(),
                        AppConstants.defaultZoom,
                        coroutineScope,
                        cameraPositionState
                    )
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
                    Tools.moveCameraTo(
                        SeixalCoords.toMapDisplay(),
                        AppConstants.defaultZoom,
                        coroutineScope,
                        cameraPositionState
                    )
                    selectedEvent = null
                },
                onCreateNewEventButtonClick = {
                    //Log.i("TEST", "Button create event clicked.")
                    (context as Activity).navigateTo(CreateEventActivity::class.java)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = screenHeight * 0.46f)
            )
        }*/
        }
    }



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventListPreview() {
    MainScreen(EventSamples.createSampleListEvents())
}