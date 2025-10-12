package com.app.matchup.ui.components.Events

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.R
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import com.app.matchup.viewmodels.CreateEventViewModel

@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = viewModel()
){
    val event by viewModel.event.collectAsState()
    val costInput by viewModel.costInput.collectAsState()


    Scaffold(
        containerColor = BACKGROUND_COLOR,

        bottomBar = {
            BottomAppBar(
                containerColor = BACKGROUND_COLOR,
                contentColor = Color.Gray,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .height(25.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "MatchUp - v.1.0.0",
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Logo and Title
                Spacer(modifier = Modifier.height(10.dp))

                // Logo
                Image(
                    painter = painterResource(R.drawable.matchup_white),
                    contentDescription = "MatchUp Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(80.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Title
                Text(
                    text = "Create New Event",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                // Create Event Form
                Box (
                    contentAlignment = Alignment.TopStart,
                ) {
                    CreateEventForm(
                        event = event,
                        costInput = costInput,
                        onNameChanged = viewModel::onNameChanged,
                        onDateChanged = viewModel::onDateChanged,
                        onCostChanged = viewModel::onCostChanged,
                        onDurationChanged = viewModel::onDurationChanged,
                        onGenderChanged = viewModel::onGenderChanged,
                        onSportChanged = viewModel::onSportChanged,
                        onMaxMembersChanged = viewModel::onMaxMembersChanged,
                        onNotesChanged = viewModel::onNotesChanged,
                        onCreateEvent = viewModel::onCreateEvent,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }

        // Little light above the logo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .size(width = 300.dp, height = 250.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFB0B0B0).copy(alpha = 0.12f),
                            Color.Transparent
                        ),
                        start = Offset(300f, 0f),
                        end = Offset(0f, 600f)
                    )
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateEventScreenPreview(){
    CreateEventScreen()
}