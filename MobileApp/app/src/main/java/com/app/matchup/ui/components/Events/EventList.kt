package com.app.matchup.ui.components.Events

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.R
import com.app.matchup.models.Event
import com.app.matchup.services.EnrollmentService
import com.app.matchup.ui.theme.EVENT_BACKGROUND_COLOR
import com.app.matchup.viewmodels.EnrollmentsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventList(
    eventList: List<Event>,
    onClickEventItem: (Event) -> Unit,
    onRefreshEventList: () -> Unit,
    onFilterEventClicked: () -> Unit,
    onEventMembersCount:suspend (Event) -> Int,
    modifier: Modifier = Modifier
){

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 360.dp)
            .background(
                color = EVENT_BACKGROUND_COLOR,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title "Near Events" , Refresh icon and Settings Icon
                Text(
                    text = "Near Events",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            color = EVENT_BACKGROUND_COLOR
                        )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Refresh Icon
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = stringResource(R.string.refresh_icon_desc),
                        tint = Color.Gray,
                        modifier = Modifier
                            .clickable(onClick = onRefreshEventList)
                    )
                    // Filter Icon
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription =  stringResource(R.string.settings_icon_desc),
                        tint = Color.Gray,
                        modifier = Modifier
                            .clickable(onClick = onFilterEventClicked)
                    )
                }
            }


            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (eventList.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.empty_event_list_textLabel),
                            color = Color.White,
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                        )
                    }
                } else {
                    itemsIndexed(eventList, key = { _, event -> event.id }) { index, event ->
                        var membersCount by remember { mutableStateOf(0) }

                        LaunchedEffect(event.id) {
                            membersCount = onEventMembersCount(event)
                        }

                        EventListItem(
                            event,
                            numberOfMembers = membersCount,
                            onClick = { onClickEventItem(event) }
                        )

                        if (index < eventList.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                thickness = 1.dp,
                                color = Color.Gray.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }
        }
    }
}