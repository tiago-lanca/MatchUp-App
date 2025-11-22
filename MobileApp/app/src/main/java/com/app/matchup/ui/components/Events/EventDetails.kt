package com.app.matchup.ui.components.Events

import android.content.Context
import android.content.Intent
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
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.app.matchup.models.Address
import com.app.matchup.models.Event
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import com.app.matchup.ui.components.ColumnWithLabel
import com.app.matchup.ui.components.FilterTag
import com.app.matchup.ui.components.Login.LoginActivity
import com.app.matchup.ui.theme.RED_BUTTON
import com.app.matchup.utilities.Tools
import com.app.matchup.services.UserSession
import com.app.matchup.viewmodels.EnrollmentsViewModel
import com.app.matchup.viewmodels.EventFiltersViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDetails(
    context: Context,
    event: Event,
    numberOfMembers: Int,
    isUserEnrolled: Boolean,
    currentUser: User?,
    onClose: (event: Event) -> Unit,
    onDeleteEvent: (event: Event) -> Unit,
    enrollmentVM: EnrollmentsViewModel = viewModel(),
    joinSnackbar: (result: Boolean) -> Unit,
    leaveEventSnackbar: (result: Boolean) -> Unit,
) {
    val dateFormatter =
        SimpleDateFormat(stringResource(R.string.date_time_pattern), Locale.getDefault())
    val isFull = numberOfMembers == event.maxMembers

    LaunchedEffect(Unit) {
        enrollmentVM.setSelectedEvent(event)
    }

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
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
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
                            .clickable { onClose(event) },
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.settings_icon_desc),
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
                        contentDescription = stringResource(R.string.location_icon_desc),
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
                    text = "${stringResource(R.string.date_hour_label)}:",
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

                    // Only shows if there's notes in that event
                    if (!event.notes.isNullOrEmpty()) {
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
                        label = "${stringResource(R.string.sports_label)}:",
                        imageIcon = event.sport?.getSportIcon()!!,
                        text = event.sport!!.name,
                    )

                    // Gender Column
                    ColumnWithLabel(
                        label = "${stringResource(R.string.gender_label)}:",
                        text = event.gender,
                        textColor = Tools.getGenderColor(event.gender),
                        textFontWeight = FontWeight.Bold
                    )

                    // Cost Column
                    ColumnWithLabel(
                        label = "${stringResource(R.string.cost_label)}:",
                        text = "${event.cost}€",
                        textFontSize = 18
                    )

                    // Duration Column
                    ColumnWithLabel(
                        label = "${stringResource(R.string.duration_label)}:",
                        text = "${event.duration}min",
                    )
                }

                // Members
                Row {
                    Column(
                        modifier = Modifier
                            .padding(top = 15.dp)
                    ) {
                        Text(
                            text = "${stringResource(R.string.max_members_label)}:",
                            color = Color.Gray
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isFull) Color.Red else Color.Green
                                    )
                                ) {
                                    // Value of enrolled members
                                    append(numberOfMembers.toString())
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = if (isFull) Color.Red else Color.Green
                                    )
                                ) {
                                    append(" / ${event.maxMembers}")
                                }
                            }
                        )

                    }
                }

                // JOIN / LEAVE / DELETE Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    // JOIN Button
                    if (!isUserEnrolled) {
                        Button(
                            colors = ButtonColors(
                                contentColor = Color.White,
                                containerColor = Color(0xFF31C848),
                                disabledContentColor = Color(0xFF31C848),
                                disabledContainerColor = Color.White
                            ),
                            onClick = {
                                if (!UserSession.isLoggedIn(context)) {
                                    val intent = Intent(context, LoginActivity::class.java)
                                    context.startActivity(intent)
                                } else {
                                    enrollmentVM.joinEvent(user = currentUser!!) { result ->
                                        joinSnackbar(result)


                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Join Event",
                                tint = Color.White,
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Text(
                                text = stringResource(R.string.join_event),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        // Leave Button
                        if (currentUser?.id != event.admin?.id) {
                            Button(
                                colors = ButtonColors(
                                    contentColor = Color.White,
                                    containerColor = RED_BUTTON,
                                    disabledContentColor = Color(0xFF31C848),
                                    disabledContainerColor = Color.White
                                ),
                                onClick = {
                                    if (!UserSession.isLoggedIn(context)) {
                                        val intent = Intent(context, LoginActivity::class.java)
                                        context.startActivity(intent)
                                    } else {
                                        enrollmentVM.leaveEvent(user = currentUser!!) { result ->
                                            leaveEventSnackbar(result)

                                            /*Toast.makeText(
                                            context,
                                            "Enrollment created successfully!",
                                            Toast.LENGTH_LONG
                                       ).apply {
                                            setGravity(Gravity.TOP,0,100)
                                            show()
                                       }*/

                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(R.string.close_icon_desc),
                                    tint = Color.White,
                                    modifier = Modifier.padding(end = 5.dp)
                                )
                                Text(
                                    text = stringResource(R.string.leave_event),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Delete Button
                        if (event.admin?.id != null && event.admin?.id == currentUser?.id) {
                            Button(
                                colors = ButtonColors(
                                    contentColor = Color.White,
                                    containerColor = Color(0xFF880202),
                                    disabledContentColor = Color(0xFF880202),
                                    disabledContainerColor = Color.White
                                ),
                                onClick = {
                                    if (!UserSession.isLoggedIn(context)) {
                                        val intent = Intent(context, LoginActivity::class.java)
                                        context.startActivity(intent)
                                    } else {
                                        onDeleteEvent(event)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DeleteForever,
                                    contentDescription = stringResource(R.string.delete_button_desc),
                                    tint = Color.White,
                                    modifier = Modifier.padding(end = 5.dp)
                                )
                                Text(
                                    text = "DELETE",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventDetailsPreview() {
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
