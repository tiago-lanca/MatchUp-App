package com.app.matchup.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.matchup.R
import com.app.matchup.extensions.getSportIcon
import com.app.matchup.models.Event
import com.app.matchup.utilities.AppConstants.DEFAULT_ZOOM
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapScreen(
    myLocation: LatLng?,
    eventList: List<Event>,
    cameraPositionState: CameraPositionState,
    onMarkerClick: (Event) -> Unit
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Runs only once is rendered
    LaunchedEffect(myLocation) {
        /*val mapCenterWithOffset = CameraPosition.fromLatLngZoom(
            LatLng(SeixalCoords.latitude - 0.004, SeixalCoords.longitude),
            DEFAULT_ZOOM)
        cameraPositionState.position = mapCenterWithOffset*/

        if(myLocation != null) {
            val mapCenterWithOffset = CameraPosition.fromLatLngZoom(
                LatLng(myLocation.latitude, myLocation.longitude),
                DEFAULT_ZOOM
            )
            cameraPositionState.position = mapCenterWithOffset
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        )
    ) {
        if(myLocation != null){
            MarkerComposable(
                state = MarkerState(position = myLocation),
                onClick = { false },
                anchor = Offset(0.5f, 0.5f)
            ){
                BlueLocationMarker()
            }
        }

        // Forces re-render of the map, erasing all markers and re-writing, avoiding the hashcode, only mapping the id
        key(eventList.map { it.id }) {

            if (eventList.isNotEmpty()) {
                eventList.forEach { event ->
                    MarkerComposable(
                        onClick = {
                            onMarkerClick(event)
                            true
                        },
                        state = MarkerState(
                            position = LatLng(
                                event.address!!.latitude!!,
                                event.address!!.longitude!!
                            )
                        )
                    ) {
                        event.sport?.getSportIcon()?.let { icon ->
                            Icon(
                                painterResource(icon),
                                modifier = Modifier.size(35.dp),
                                contentDescription = stringResource(R.string.sport_icon_des),
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun MapScreenPreview() {
    //MapScreen()
}