package com.app.matchup.ui.components.My_Events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R
import com.app.matchup.enums.ShowMyEvents
import com.app.matchup.ui.components.LightFromAbove
import com.app.matchup.ui.theme.BACKGROUND_COLOR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyEventsScreen(

) {
    Scaffold(
        containerColor = BACKGROUND_COLOR,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.go_back_button_desc),
                        tint = Color.White,
                        modifier = Modifier.padding(10.dp)
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


        var selectedButton by remember { mutableStateOf(ShowMyEvents.ACTIVE) }

        /*LaunchedEffect(selectedButton) {

        }*/

        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                modifier = Modifier
                    .fillMaxWidth()
            ){
                // Active button
                MyEventsTopButton(
                    text = "Active",
                    isSelected = selectedButton == ShowMyEvents.ACTIVE,
                    icon = Icons.Default.AccessTime,
                    contentDescription = stringResource(R.string.check_icon_desc),
                    onButtonClick = { selectedButton = ShowMyEvents.ACTIVE },
                    modifier = Modifier
                )

                // Completed button
                MyEventsTopButton(
                    text = "Completed",
                    isSelected = selectedButton == ShowMyEvents.COMPLETED,
                    icon = Icons.Default.TaskAlt,
                    contentDescription = stringResource(R.string.check_icon_desc),
                    onButtonClick = { selectedButton = ShowMyEvents.COMPLETED },
                    modifier = Modifier
                )

                // Show all button
                MyEventsTopButton(
                    text = "Show all",
                    isSelected = selectedButton == ShowMyEvents.ALL,
                    icon = Icons.AutoMirrored.Filled.List,
                    contentDescription = stringResource(R.string.menu_icon_desc),
                    onButtonClick = { selectedButton = ShowMyEvents.ALL },
                    modifier = Modifier
                )
            }
        }
    }
    LightFromAbove()

}


@Preview
@Composable
fun MyEventsScreenPreview() {
    MyEventsScreen()
}