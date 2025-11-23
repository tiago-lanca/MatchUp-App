package com.app.matchup.ui.components.My_Events

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.MainActivity
import com.app.matchup.R
import com.app.matchup.enums.ShowMyEventsType
import com.app.matchup.enums.Status
import com.app.matchup.models.User
import com.app.matchup.services.EnrollmentService
import com.app.matchup.ui.components.Events.EventListItem
import com.app.matchup.ui.components.LightFromAbove
import com.app.matchup.ui.components.MainMenu.MainMenuActivity
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.ui.theme.EVENT_BACKGROUND_COLOR
import com.app.matchup.ui.theme.RED_BUTTON
import com.app.matchup.ui.theme.SIGNIN_BUTTON_COLOR
import com.app.matchup.viewmodels.EnrollmentsViewModel
import com.app.matchup.viewmodels.EventsViewModel
import com.app.matchup.viewmodels.MyEventsViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyEventsScreen(
    current_user: User,
    context: Context,
    myEventsVM: MyEventsViewModel = viewModel(),
    eventsVM: EventsViewModel = viewModel(),
    enrollmentVM: EnrollmentsViewModel = viewModel()
) {
    val myEvents by myEventsVM.myEventsList.collectAsState()
    val selectedEvent by myEventsVM.myEventSelected.collectAsState()
    val isLoading by myEventsVM.isLoading.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedButton by remember { mutableStateOf(ShowMyEventsType.ACTIVE) }


    Scaffold(
        containerColor = BACKGROUND_COLOR,
        floatingActionButton = {
            if(selectedEvent == null) {
                ExtendedFloatingActionButton(
                    onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        if(context is Activity) context.finish()
                    },
                    text = {
                        Text(
                            text = "Search events",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                   },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    containerColor = SIGNIN_BUTTON_COLOR,
                    contentColor = Color.White,
                    modifier = Modifier
                        .height(40.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.go_back_button_desc),
                        tint = Color.White,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable{
                                (context as Activity).finish()
                            }
                    )

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BACKGROUND_COLOR
                ),
                title = {
                    Text(
                        text = "My Events",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }
    ) { innerPadding ->

        LaunchedEffect(selectedButton) {

            myEventsVM.loadMyEvents(selectedButton, current_user)
            myEventsVM.setSelectedEvent(null)
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Active button
                MyEventsTopButton(
                    text = "Active",
                    isSelected = selectedButton == ShowMyEventsType.ACTIVE,
                    icon = Icons.Default.AccessTime,
                    contentDescription = stringResource(R.string.check_icon_desc),
                    onButtonClick = { selectedButton = ShowMyEventsType.ACTIVE },
                    modifier = Modifier
                )

                // Completed button
                MyEventsTopButton(
                    text = "Completed",
                    isSelected = selectedButton == ShowMyEventsType.COMPLETED,
                    icon = Icons.Default.TaskAlt,
                    contentDescription = stringResource(R.string.check_icon_desc),
                    onButtonClick = { selectedButton = ShowMyEventsType.COMPLETED },
                    modifier = Modifier
                )

                // Show all button
                MyEventsTopButton(
                    text = "Show all",
                    isSelected = selectedButton == ShowMyEventsType.ALL,
                    icon = Icons.AutoMirrored.Filled.List,
                    contentDescription = stringResource(R.string.menu_icon_desc),
                    onButtonClick = { selectedButton = ShowMyEventsType.ALL },
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.size(15.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                if(isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                        }
                    }
                }

                if (!isLoading && myEvents.isEmpty()) {
                    item {
                        Text(
                            text = "Event list is empty.",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    itemsIndexed(myEvents, key = { _, event -> event.id }) { index, event ->
                        var membersCount by remember { mutableStateOf(0) }

                        LaunchedEffect(event.id) {
                            membersCount = EnrollmentService.getEnrollmentsByEventId(event.id) ?: 0
                        }

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = EVENT_BACKGROUND_COLOR,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (selectedEvent == event)
                                        myEventsVM.setSelectedEvent(null)
                                    else if(selectedEvent != event && event.status != Status.CLOSED)
                                        myEventsVM.setSelectedEvent(event)
                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 0.dp)
                            ) {
                                EventListItem(
                                    event,
                                    numberOfMembers = membersCount,
                                    onClick = {
                                        if (selectedEvent == event)
                                            myEventsVM.setSelectedEvent(null)
                                        else if(selectedEvent != event && event.status != Status.CLOSED)
                                            myEventsVM.setSelectedEvent(event)
                                    },
                                    arrowIcon =
                                        if(event.status == Status.CLOSED){
                                            Icons.Filled.Check
                                        }
                                        else {
                                            if (selectedEvent == event) Icons.Filled.KeyboardArrowDown
                                            else Icons.Filled.ChevronRight
                                        },
                                    arrowTint = if(event.status == Status.CLOSED) Color.Red else Color.White
                                )

                            }
                            if (selectedEvent == event && event.status == Status.OPEN) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        5.dp,
                                        Alignment.CenterHorizontally
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    if (current_user.id == event.admin?.id) {
                                        // Delete Button
                                        Button(
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = RED_BUTTON,
                                                contentColor = Color.White
                                            ),
                                            onClick = {
                                                eventsVM.deleteEvent { success ->
                                                    if (success) {
                                                        scope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                context.getString(R.string.event_deleted_message)
                                                            )
                                                        }
                                                        myEventsVM.loadMyEvents(selectedButton, current_user)
                                                    }
                                                }
                                            }
                                        ) {
                                            Text("Delete")
                                        }
                                    } else {
                                        // Leave Button
                                        Button(
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = RED_BUTTON,
                                                contentColor = Color.White
                                            ),
                                            onClick = {
                                                // Assigns an event to the selected event in Enrollment view model
                                                enrollmentVM.setSelectedEvent(event)
                                                enrollmentVM.leaveEvent(current_user){ success ->
                                                    if(success){
                                                        scope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                context.getString(R.string.user_left_event_message)
                                                            )
                                                        }
                                                        eventsVM.setUserEnrolled(false)
                                                        myEventsVM.loadMyEvents(selectedButton, current_user)
                                                    }
                                                }

                                            }
                                        ) {
                                            Text(text = "Leave")
                                        }
                                    }


                                    // See in map button
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = SIGNIN_BUTTON_COLOR,
                                            contentColor = Color.White
                                        ),
                                        onClick = {
                                            val intent =
                                                Intent(context, MainActivity::class.java)
                                            intent.putExtra("createdEvent", event)
                                            context.startActivity(intent)
                                        }
                                    ) {
                                        Text(text = "See in map")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    LightFromAbove()

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


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MyEventsScreenPreview() {
    MyEventsScreen(
        current_user = User(),
        context = LocalContext.current
    )
}