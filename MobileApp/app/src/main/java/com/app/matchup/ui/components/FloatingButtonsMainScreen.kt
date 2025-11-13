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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.matchup.R
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
                .shadow(6.dp, CircleShape)

        ) {
            Icon(
                imageVector = Icons.Default.NearMe,
                contentDescription = stringResource(R.string.go_to_my_location_icon_desc)
            )
        }


        // Create New Event Button
        FloatingActionButton(
            onClick = { onCreateNewEventButtonClick() },
            containerColor = Color(0xFF04A138),
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .size(46.dp)
                .shadow(6.dp, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.create_new_event_icon_desc)
            )
        }
    }
}
