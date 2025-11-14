package com.app.matchup.ui.components.Events

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.R
import com.app.matchup.extensions.getSportIcon
import com.app.matchup.extensions.getSportIconSize
import com.app.matchup.models.Event
import com.app.matchup.ui.theme.EVENT_BACKGROUND_COLOR
import com.app.matchup.viewmodels.EventsViewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventListItem(
    event: Event,
    numberOfMembers: Int,
    onClick: () -> Unit,
){

    val context = LocalContext.current
    val dateFormatter = DateTimeFormatter.ofPattern(stringResource(R.string.date_pattern))


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(EVENT_BACKGROUND_COLOR)
            .padding(horizontal = 0.dp, vertical = 10.dp)
            .clickable { onClick() }
    ){
        Image(
            painter = painterResource(event.sport?.getSportIcon()!!),
            contentDescription = stringResource(R.string.sport_icon_des),
            modifier = Modifier
                .padding(end = 15.dp)
                .size(event.sport?.getSportIcon()!!.getSportIconSize()),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row {
                // Address Icon
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = stringResource(R.string.location_icon_desc),
                    tint = Color.Red
                )
                Column(
                    modifier = Modifier
                        .padding(start = 5.dp)
                ) {
                    // Address Street
                    Text(
                        text = event.address!!.street,
                        color = Color.White
                    )
                    // City
                    Text(
                        text = event.address!!.city,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        lineHeight = 10.sp
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 5.dp),
            ) {
                // Hour icon
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = stringResource(R.string.schedule_icon_desc),
                    tint = Color.White
                )
                // Date Hour
                Text(
                    text = SimpleDateFormat("${context.getString(R.string.date_time_pattern)}'h'",
                        Locale.getDefault())
                        .format(event.date!!),
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Groups,
                        contentDescription = stringResource(R.string.members_of_event_label) ,
                        tint = Color.White
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Green
                                )
                            ) {
                                append(numberOfMembers.toString())
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Green
                                )
                            ) {
                                append(" /${event.maxMembers}")
                            }
                        },
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                }
            }
        }

        IconButton(
            onClick = {
                onClick()

                /*val intent = Intent(context, EventDetailsActivity::class.java).apply {
                    putExtra("event_id", event.id.toString())
                    putExtra("event_name", event.name)
                }

                context.startActivity(intent)
                if(context is Activity) context.finish()*/
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = stringResource(R.string.go_to_event_details),
                tint = Color.White,
                modifier = Modifier
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventListItemPreview() {
    //EventListItem(event = EventSamples.createSampleEvent())
}