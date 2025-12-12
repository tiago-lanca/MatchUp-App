package com.app.matchup.ui.components.Events

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.foundation.layout.only
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.app.matchup.MainMenuActivity
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.app.matchup.R
import com.app.matchup.extensions.isNull
import com.app.matchup.models.User
import com.app.matchup.services.EnrollmentService
import com.app.matchup.services.UserService
import com.app.matchup.ui.components.Login.LoginActivity
import com.app.matchup.utilities.AppConstants.DEFAULT_ZOOM
import com.app.matchup.utilities.AppConstants.EVENT_ZOOMED
import com.app.matchup.services.UserSession
import com.app.matchup.ui.components.Filters.FilterEventBottomSheet
import com.app.matchup.utilities.AppConstants.IadeCoords
import com.app.matchup.utilities.EventFilterSession
import com.app.matchup.utilities.Tools.getCurrentLocation
import com.app.matchup.viewmodels.EventFiltersViewModel
import com.google.android.gms.maps.CameraUpdateFactory

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    context: Context,
    eventsVM: EventsViewModel = viewModel(),
    filtersVM: EventFiltersViewModel = viewModel(),
    event: Event? = null
) {
    val context = LocalContext.current
    var currentUser by remember { mutableStateOf<User?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val eventList by eventsVM.events.collectAsState()
    val selectedEvent by eventsVM.selectedEvent.collectAsState()
    val numberOfMembers by eventsVM.numberOfMembers.collectAsState()
    val isUserEnrolled by eventsVM.isUserEnrolled.collectAsState()
    val isLoading by eventsVM.isLoading.collectAsState()

    val filters by filtersVM.filters.collectAsState()
    var showFilterEventSheet by remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded
        )
    )
    val sheetState = scaffoldState.bottomSheetState
    val density = LocalDensity.current
    var sheetOffsetDp by remember { mutableStateOf(0.dp) }

    var myLocation by remember { mutableStateOf<LatLng?>(null) }
    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermission =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    LaunchedEffect("permissions") {
        val fineGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        hasPermission = fineGranted || coarseGranted

        if(!hasPermission){
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(hasPermission) {
        if(hasPermission){
            getCurrentLocation(context){ latLng ->
                myLocation = if(AppConstants.USE_REAL_LOCATION) latLng else IadeCoords

                if(selectedEvent == null){
                    Tools.moveCameraTo(
                        latLng = LatLng(
                            myLocation?.latitude!!,
                            myLocation?.longitude!!
                        ),
                        coroutineScope = coroutineScope,
                        cameraPositionState = cameraPositionState
                    )
                }
            }
        }
    }

    LaunchedEffect(sheetState) {
        snapshotFlow { sheetState.requireOffset() }
            .collect { offsetPx ->
                sheetOffsetDp = with(density) { offsetPx.toDp() }
            }
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sheetVisibleHeight = screenHeight - sheetOffsetDp

    LaunchedEffect(Unit) {
        filtersVM.setFilters(EventFilterSession.filters)
        eventsVM.loadFilteredEvents(filtersVM.filters.value, context)

        // On MainActivity starting, checks if there's any event created passed by CreateEventActivity or MyEventsActivity
        // If there's any, then select it and move the camera to the address
        if (event != null) {
            // Adds the admin user to the event object
            val eventCreated = event.copy(admin = EventService.getEventAdmin(event.id))
            eventsVM.selectEvent(eventCreated)
            eventsVM.getNumberOfEnrollmentsOnSelectedEvent()

            currentUser = UserSession.getUser(context)
            eventsVM.isUserEnrolled(context, currentUser!!.id)

            eventCreated.address?.let { address ->
                Tools.moveCameraTo(
                    latLng = LatLng(
                        address.latitude!!,
                        address.longitude!!
                    ),
                    coroutineScope = coroutineScope,
                    cameraPositionState = cameraPositionState
                )
            }

            filtersVM.reset()
        }

        // Loads current user
        if(currentUser == null) {
            currentUser = UserSession.getUser(context)
        }
    }

    LaunchedEffect(eventList) {
        eventsVM.loadMembersCountOnEvents(eventList)
    }

    LaunchedEffect(selectedEvent) {
        eventsVM.getNumberOfEnrollmentsOnSelectedEvent()

        if(currentUser != null) {
            eventsVM.isUserEnrolled(context, currentUser!!.id)
        }
    }

    Scaffold { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFB0BEC5))
        ) {

            // Near Events Section
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = 220.dp,
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
                        if (isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color.White)
                            }
                        } else {
                            if (selectedEvent.isNull()) {
                                EventList(
                                    eventsVM = eventsVM,
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
                                        eventsVM.selectEvent(event)
                                    },
                                    onEventMembersCount = { event ->
                                        EnrollmentService.getEnrollmentsByEventId(event.id)!!
                                    },
                                    onRefreshEventList = {
                                        coroutineScope.launch {
                                            eventsVM.loadFilteredEvents(
                                                filters,
                                                context
                                            ) { success ->
                                                if (success) {
                                                    scope.launch {
                                                        snackbarHostState.showSnackbar(
                                                            context.getString(R.string.refresh_event_list_message)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    onFilterEventClicked = {
                                        showFilterEventSheet = true
                                    },
                                    onFilterRemoved = {
                                        eventsVM.loadFilteredEvents(
                                            filtersVM.filters.value,
                                            context
                                        )
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
                                        eventsVM.selectEvent(null)
                                    },
                                    onDeleteEvent = { event ->
                                        eventsVM.deleteEvent { success ->
                                            if (success) {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar(
                                                        context.getString(R.string.event_deleted_message)
                                                    )
                                                }
                                                eventsVM.selectEvent(null)
                                                eventsVM.loadFilteredEvents(filters, context)
                                            }
                                        }
                                    },
                                    joinSnackbar = { success ->
                                        if (success) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    context.getString(R.string.enrollment_created_message)
                                                )
                                            }
                                            eventsVM.setUserEnrolled(true)
                                            eventsVM.getNumberOfEnrollmentsOnSelectedEvent()
                                            eventsVM.loadFilteredEvents(filters, context)
                                            eventsVM.loadMembersCountOnEvents(eventList)
                                        }
                                    },
                                    leaveEventSnackbar = { success ->
                                        if (success) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    context.getString(R.string.user_left_event_message)
                                                )
                                            }
                                            eventsVM.setUserEnrolled(false)
                                            eventsVM.getNumberOfEnrollmentsOnSelectedEvent()
                                            eventsVM.loadFilteredEvents(filters, context)
                                        }
                                    }
                                )
                            }
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
                        myLocation = myLocation,
                        eventList = eventList,
                        cameraPositionState = cameraPositionState,
                        onMarkerClick = { event ->
                            eventsVM.selectEvent(event)

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
                            contentDescription = stringResource(R.string.open_menu_icon_desc)
                        )
                    }


                    // Create New Event button and MyLocation button
                    FloatingButtonsMainScreen(
                        onMyLocationButtonClick = {
                            if (!hasPermission) {
                                permissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            } else {
                                getCurrentLocation(context) { latLng ->
                                    if (latLng != null) {
                                        myLocation = if (AppConstants.USE_REAL_LOCATION) latLng else IadeCoords

                                        Tools.moveCameraTo(
                                            latLng = if (AppConstants.USE_REAL_LOCATION) latLng else IadeCoords,
                                            zoom = AppConstants.DEFAULT_ZOOM,
                                            coroutineScope,
                                            cameraPositionState
                                        )
                                    }
                                }
                            }
                        },
                        onCreateNewEventButtonClick = {
                            if (currentUser != null) {
                                val intent = Intent(context, SelectLocationActivity::class.java)
                                intent.putExtra("my_location", myLocation)
                                context.startActivity(intent)
                            } else (context as Activity).navigateTo(
                                activity = LoginActivity::class.java,
                                closeCurrentActivity = false
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(
                                end = 10.dp, bottom = (screenHeight - sheetOffsetDp - 200.dp).coerceAtLeast(24.dp)
                            )
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

                if (showFilterEventSheet) {
                    FilterEventBottomSheet(
                        context = context,
                        onDismiss = { showFilterEventSheet = false },
                        onApplyFilters = { filters ->
                            eventsVM.loadFilteredEvents(filters, context)
                        }
                    )
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventListPreview() {
    MainScreen(LocalContext.current
    )
}