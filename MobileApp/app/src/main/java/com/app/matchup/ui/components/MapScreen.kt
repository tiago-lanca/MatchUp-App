package com.app.matchup.ui.components

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.matchup.models.Event
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.app.matchup.R
import com.app.matchup.extensions.getSportIcon
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapScreen(
    eventList: List<Event>,
    cameraPositionState: CameraPositionState,
    onMarkerClick: (Event) -> Unit
) {

    val seixalCoords = LatLng(38.621759, -9.105657)
    val defaultZoom = 15f

    // Runs only once is rendered
    LaunchedEffect(Unit) {
        val mapCenterWithOffset = CameraPosition.fromLatLngZoom(
            LatLng(seixalCoords.latitude - 0.004, seixalCoords.longitude),
            defaultZoom)
        cameraPositionState.position = mapCenterWithOffset
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        )
    ) {
        if(eventList.isNotEmpty()) {
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
                    ),
                    //title = "Seixal",
                    //snippet = "Seixal"
                    //icon = BitmapDescriptorFactory.fromBitmap(customIcon)
                ){
                    Icon(
                        painterResource(event.sport?.getSportIcon()!!),
                        modifier = Modifier.size(35.dp),
                        contentDescription = "Football Icon",
                        tint = Color.Unspecified
                    )
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