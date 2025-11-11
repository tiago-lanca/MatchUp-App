package com.app.matchup.ui.components.Events

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.SelectLocationActivity
import com.app.matchup.extensions.toMapDisplay
import com.app.matchup.models.Event
import com.app.matchup.services.EventService
import com.app.matchup.ui.components.FloatingButtonsMainScreen
import com.app.matchup.ui.components.MainMenu.MainMenuActivity
import com.app.matchup.ui.components.MapScreen
import com.app.matchup.ui.theme.EVENT_BACKGROUND_COLOR
import com.app.matchup.utilities.AppConstants
import com.app.matchup.utilities.AppConstants.MAP_DISPLAY_OFFSET
import com.app.matchup.utilities.AppConstants.SeixalCoords
import com.app.matchup.utilities.Tools
import com.app.matchup.utilities.Tools.navigateTo
import com.app.matchup.viewmodels.EventsViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.matchup.models.User
import com.app.matchup.services.EnrollmentService
import com.app.matchup.ui.components.Login.LoginActivity
import com.app.matchup.utilities.AppConstants.DEFAULT_ZOOM
import com.app.matchup.utilities.AppConstants.EVENT_ZOOMED
import com.app.matchup.utilities.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    context: Context,
    viewModel: EventsViewModel = viewModel(),
    event: Event? = null
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val context = LocalContext.current
    var currentUser by remember { mutableStateOf<User?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val numberEvents = viewModel.events.collectAsState().value.size
    val eventList by viewModel.events.collectAsState()
    val selectedEvent by viewModel.selectedEvent.collectAsState()
    val numberOfMembers by viewModel.numberOfMembers.collectAsState()
    val isUserEnrolled by viewModel.isUserEnrolled.collectAsState()

    val cameraPositionState = rememberCameraPositionState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded
        )
    )

    val sheetState = scaffoldState.bottomSheetState

    val fabPadding by animateDpAsState(
        when (sheetState.currentValue) {
            SheetValue.Expanded ->
                if (selectedEvent.isNull()) 240.dp else 290.dp

            SheetValue.PartiallyExpanded -> 10.dp
            else -> 180.dp
        }
    )


    LaunchedEffect(Unit) {
        // On MainActivity starting, checks if there's any event created passed by CreateEventActivity
        // If there's any, then select it and move the camera to the address
        if (event != null) {
            // Adds the admin user to the event object
            val eventCreated = event.copy(admin = EventService.getEventAdmin(event.id))
            viewModel.selectEvent(eventCreated)

            eventCreated.address?.let { address ->
                Tools.moveCameraTo(
                    latLng = LatLng(
                        address.latitude!! - MAP_DISPLAY_OFFSET,
                        address.longitude!!
                    ),
                    coroutineScope = coroutineScope,
                    cameraPositionState = cameraPositionState
                )
            }
        }
        // Loads current user
        currentUser = UserSession.getUser(context)
    }

    LaunchedEffect(selectedEvent) {
        viewModel.getNumberOfMembersEnrolledInCurrentEvent()

        if(currentUser != null) {
            viewModel.isUserEnrolled(context, currentUser!!.id)
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
                    if (selectedEvent.isNull()) {
                        EventList(
                            eventList = eventList,
                            onClickEventItem = { event ->
                                event.address?.let { address ->
                                    Tools.moveCameraTo(
                                        latLng = LatLng(
                                            address.latitude!! - MAP_DISPLAY_OFFSET / EVENT_ZOOMED,
                                            address.longitude!!,
                                        ),
                                        zoom = EVENT_ZOOMED,
                                        coroutineScope = coroutineScope,
                                        cameraPositionState = cameraPositionState
                                    )
                                }
                                viewModel.selectEvent(event)
                            },
                            onEventMembersCount = { event ->
                                EnrollmentService.getEnrollmentsByEventId(event.id)!!
                            },
                            onRefreshEventList = {
                                coroutineScope.launch {
                                    viewModel.loadEvents()
                                }
                            }
                        )
                    } else {
                        EventDetails(
                            context,
                            event = selectedEvent!!,
                            numberOfMembers = numberOfMembers,
                            isUserEnrolled = isUserEnrolled,
                            currentUser = currentUser,
                            onClose = { event ->
                                Tools.moveCameraTo(
                                    latLng = LatLng(
                                        event.address?.latitude!! - (MAP_DISPLAY_OFFSET / DEFAULT_ZOOM),
                                        event.address?.longitude!!
                                    ),
                                    zoom = DEFAULT_ZOOM,
                                    coroutineScope = coroutineScope,
                                    cameraPositionState = cameraPositionState
                                )
                                viewModel.selectEvent(null)
                            },
                            onDeleteEvent = { event ->
                                viewModel.deleteEvent()
                            },
                            joinSnackbar = { success ->
                                if(success){
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Enrollment created successfully!"
                                        )
                                    }
                                    viewModel.setUserEnrolled(true)
                                    viewModel.getNumberOfMembersEnrolledInCurrentEvent()
                                }
                            },
                            leaveEventSnackbar = { success ->
                                if(success){
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "You have left the event with success."
                                        )
                                    }
                                    viewModel.setUserEnrolled(false)
                                    viewModel.getNumberOfMembersEnrolledInCurrentEvent()
                                }
                            }
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
                        viewModel.selectEvent(event)

                        Tools.moveCameraTo(
                            LatLng(
                                event.address?.latitude!! - MAP_DISPLAY_OFFSET / EVENT_ZOOMED,
                                event.address?.longitude!!
                            ),
                            AppConstants.EVENT_ZOOMED,
                            coroutineScope,
                            cameraPositionState
                        )
                    }
                )
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(context, MainMenuActivity::class.java)
                        context.startActivity(intent)
                        if (context is Activity) context.finish()
                    },
                    containerColor = Color.Black.copy(alpha = 0.9f),
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .statusBarsPadding()
                        .padding(start = 10.dp)
                        .size(46.dp)
                        .zIndex(2f)
                        .border(1.dp, Color.White, CircleShape),
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Open menu icon"
                    )
                }


                // Create New Event button
                FloatingButtonsMainScreen(
                    onMyLocationButtonClick = {
                        Tools.moveCameraTo(
                            SeixalCoords.toMapDisplay(),
                            AppConstants.DEFAULT_ZOOM,
                            coroutineScope,
                            cameraPositionState
                        )
                    },
                    onCreateNewEventButtonClick = {
                        if(UserSession.isLoggedIn(context)) {
                            (context as Activity).navigateTo(
                                activity = SelectLocationActivity::class.java,
                                closeCurrentActivity = false
                            )
                        }
                        else (context as Activity).navigateTo(
                            activity = LoginActivity::class.java,
                            closeCurrentActivity = false
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 10.dp, bottom = fabPadding)
                )

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 50.dp)
                ) { data ->
                    val isSuccess = data.visuals.message.contains("success", ignoreCase = true)

                    Snackbar(
                        containerColor = if (isSuccess) Color(0xFF025D14) else Color(0xFF880202),
                        contentColor = Color.White,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .widthIn(max = 300.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = if (isSuccess) Icons.Default.Check else Icons.Default.Close,
                                tint = if (isSuccess) Color(0xFFFFFFFF) else Color(0xFF000000),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )

                            Text(
                                text = data.visuals.message,
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                }
            }
        }
    }
}

fun Event?.isNull() = this == null


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventListPreview() {
    MainScreen(LocalContext.current
    )
}