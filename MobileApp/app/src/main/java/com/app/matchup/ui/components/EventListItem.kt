package com.app.matchup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R

@Composable
fun EventListItem(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0xFF282828))
    ){
        Image(
            painter = painterResource(R.drawable.football_icon),
            contentDescription = "Football Icon",
            modifier = Modifier
                .padding(end = 15.dp)
                .height(height = 30.dp)
        )

        Column {
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
                        text = "Rua test teste nº10",
                        color = Color.White
                    )
                    Text(
                        text = "Moscavide",
                        color = Color.Gray,
                        fontSize = 10.sp
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
                    contentDescription = "Location Icon",
                    tint = Color.White
                )
                Text(
                    text = "01/12/2025  18:00h",
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )

                Icon(
                    imageVector = Icons.Filled.Groups,
                    contentDescription = "Members of the event",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 20.dp)
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
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = "Click to see respective event details",
            tint = Color.White,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EventListItemPreview() {
    EventListItem()
}