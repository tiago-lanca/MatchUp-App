package com.app.matchup.ui.components.Events

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
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
import com.app.matchup.extensions.getSportIcon
import com.app.matchup.models.Address
import com.app.matchup.models.Event
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import com.app.matchup.ui.components.ColumnWithLabel
import com.app.matchup.ui.components.MapScreen
import com.app.matchup.utilities.Tools
import com.google.maps.android.compose.rememberCameraPositionState
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDetails(
    event: Event,
    onClose: () -> Unit
){
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val cameraPositionState = rememberCameraPositionState()

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color(0xFF282828),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            Column {
                // Row of Event Name and Close Icon
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = event.name,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Icon(
                        modifier = Modifier
                            .clickable { onClose() },
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }


                // Row of Location Icon and Address
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        tint = Color.Red,
                        contentDescription = "Location Icon",
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp)
                    ) {
                        Text(
                            text = event.address!!.street,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "${event.address!!.zipCode} ${event.address?.city}",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }

                Text(
                    text = "Date/Hour:",
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 20.dp)
                )

                // Row of Date and Hour, and Information Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${dateFormatter.format(event.date)}h",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    /*Text(
                        text = SimpleDateFormat("HH':'mm'h'", Locale.getDefault()).format(event.date),
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )*/

                    // Only shows if there's notes in that event
                    if(!event.notes.isNullOrEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.information_icon_blue),
                            contentDescription = "Information Icon",
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(30.dp)
                                .clickable { }
                        )
                    }
                }

                // Row of Sport, Genre, Cost and Duration
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)

                ) {
                    // Sport Column
                    ColumnWithLabel(
                        label = "Sport:",
                        imageIcon = event.sport?.getSportIcon()!!,
                        text = event.sport!!.name,
                    )

                    // Genre Column
                    ColumnWithLabel(
                        label = "Genre:",
                        text = event.gender,
                        textColor = Tools.getGenderColor(event.gender),
                        textFontWeight = FontWeight.Bold
                    )

                    // Cost Column
                    ColumnWithLabel(
                        label = "Cost:",
                        text = "${event.cost.toString()}€",
                        textFontSize = 18
                    )


                    // Duration Column
                    ColumnWithLabel(
                        label = "Duration:",
                        text = "${event.duration}min",
                    )
                }

                Row {
                    Column(
                        modifier = Modifier
                            .padding(top = 15.dp)
                    ) {
                        Text(
                            text = "Members:",
                            color = Color.Gray
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Green
                                    )
                                ) {
                                    // Value of enrolled members
                                    append("1")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.Green
                                    )
                                ) {
                                    append(" / ${event.maxMembers}")
                                }
                            }
                        )

                    }
                }

                // TODO() If has no login then disable button
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        colors = ButtonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFF31C848),
                            disabledContentColor = Color(0xFF31C848),
                            disabledContainerColor = Color.White
                        ),
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Join Event",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        Text(
                            text = "JOIN",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventDetailsPreview(){
    val user = User(
        id = UUID.randomUUID(),
        name = "Tiago Lança",
        email = "tiagotestest@email.com",
        passwordHash = "1234"
    )
    val event = Event(
        id = UUID.randomUUID(),
        name = "Test Event",
        date = Date(),
        address = Address(
            id = UUID.randomUUID(),
            street = "Rua dos Testes n10",
            zipCode = "1886-502",
            city = "Seixal"
        ),
        cost = 3.0,
        duration = 60,
        gender = "M",
        sport = Sport(
            id = UUID.randomUUID(),
            name = "Football"
        ),
        admin = user,
        notes = "This is a test event"
    )
    //EventDetails(event = event)
}