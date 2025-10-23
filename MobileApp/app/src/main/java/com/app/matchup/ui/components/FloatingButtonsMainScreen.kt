package com.app.matchup.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.matchup.utilities.Tools
import com.google.android.gms.maps.CameraUpdateFactory
import kotlinx.coroutines.launch

@Composable
fun FloatingButtonsMainScreen(
    onMyLocationButtonClick: () -> Unit,
    onCreateNewEventButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.End
    ) {
        //  My Location Button
        FloatingActionButton(
            onClick = { onMyLocationButtonClick() },
            containerColor = Color(0xFF2C85FF),
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .size(46.dp)
        ) {
            Icon(
                imageVector = Icons.Default.NearMe,
                contentDescription = "Go to my location"
            )
        }


        // Create New Event Button
        FloatingActionButton(
            onClick = { onCreateNewEventButtonClick() },
            containerColor = Color(0xFF04A138),
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier.size(46.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Create new event icon"
            )
        }
    }
}
