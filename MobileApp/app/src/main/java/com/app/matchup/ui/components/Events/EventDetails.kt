package com.app.matchup.ui.components.Events

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
import com.app.matchup.ui.components.ColumnWithLabel

@Composable
fun EventDetails(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB0BEC5))
    ) {

        // Google Maps on the background

        // Near Events Section
        //Todo() Create model Event and fill with data to send here
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
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
                        text = "Event Name",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }

                Text(
                    text = "#123456789",
                    color = Color.Gray
                )

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
                            text = "Rua test test test nº10",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "1886-502 Moscavide",
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
                        text = "01/12/2025",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "18:00h",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )


                    // Only shows if there's notes in that event
                    Image(
                        painter = painterResource(R.drawable.information_icon_blue),
                        contentDescription = "Information Icon",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(30.dp)
                            .clickable { }
                    )
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
                        imageIcon = "football_icon",
                        text = "Football",
                    )

                    // Genre Column
                    ColumnWithLabel(
                        label = "Genre:",
                        text = "M",
                        textColor = Color(0xFF1E90FF),
                        textFontWeight = FontWeight.Bold
                    )

                    // Cost Column
                    ColumnWithLabel(
                        label = "Cost:",
                        text = "3€",
                        textFontSize = 18
                    )


                    // Duration Column
                    ColumnWithLabel(
                        label = "Duration:",
                        text = "90min",
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
                                    append("10")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.Green
                                    )
                                ) {
                                    append(" / 12")
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


@Preview(showBackground = true)
@Composable
fun EventDetailsPreview(){
    EventDetails()
}