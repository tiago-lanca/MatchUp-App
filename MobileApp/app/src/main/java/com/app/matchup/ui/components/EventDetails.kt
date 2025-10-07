package com.app.matchup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R

@Composable
fun EventDetails(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB0BEC5))
    ) {

        // Google Maps on the background

        // Near Events Section
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
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
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
                Row (
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
                    Column (
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
                Row (
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
                            .clickable{  }
                    )
                }

                // Row of Sport, Genre, Cost and Duration
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)

                ) {
                    // Sport Column
                    Column (
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Sport:",
                            color = Color.Gray
                        )
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 5.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.football_icon),
                                contentDescription = "Football Icon",
                                modifier = Modifier
                                    .size(25.dp)
                            )
                            Text(
                                text = "Football",
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                            )
                        }
                    }
                    // Genre Column
                    Column (
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Genre:",
                            color = Color.Gray
                        )
                        Text(
                            text = "M",
                            color = Color(0xFF1E90FF),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
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