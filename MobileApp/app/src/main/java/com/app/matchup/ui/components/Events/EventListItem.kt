package com.app.matchup.ui.components.Events

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R
import com.app.matchup.models.Address
import com.app.matchup.models.Event
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import com.app.matchup.samples.EventSamples
import com.app.matchup.ui.theme.BACKGROUND_COLOR
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventListItem(event: Event){

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(BACKGROUND_COLOR)
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ){
        Image(
            painter = painterResource(R.drawable.football_icon),
            contentDescription = "Football Icon",
            modifier = Modifier
                .padding(end = 15.dp)
                .height(height = 30.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Location Icon",
                    tint = Color.Red
                )

                Column(
                    modifier = Modifier
                        .padding(start = 5.dp)
                ) {
                    Text(
                        text = event.address.street,
                        color = Color.White
                    )
                    Text(
                        text = event.address.city,
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
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = "Schedule Icon",
                    tint = Color.White
                )
                Text(
                    text = "${event.date.format(dateFormatter)} ${event.date.hour}:${event.date.minute}h",
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
                        contentDescription = "Members of the event",
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
                                append("10")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Green
                                )
                            ) {
                                append("/12")
                            }
                        },
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                }
            }
        }

        IconButton(
            onClick = { },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Click to see respective event details",
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
    EventListItem(event = EventSamples.createSampleEvent())
}