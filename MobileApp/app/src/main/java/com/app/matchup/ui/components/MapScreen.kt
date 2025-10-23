package com.app.matchup.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapScreen(
    cameraPositionState: CameraPositionState
) {

    val seixalCoords = LatLng(38.621759, -9.105657)
    val defaultZoom = 15f


    // Runs only once is rendered
    LaunchedEffect(Unit) {
        val mapCenterWithOffset = CameraPosition.fromLatLngZoom(
            LatLng(seixalCoords.latitude - 0.003, seixalCoords.longitude),
            defaultZoom)
        cameraPositionState.position = mapCenterWithOffset
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = seixalCoords),
            title = "Seixal",
            snippet = "Seixal"
        )
    }

}

@Preview
@Composable
fun MapScreenPreview() {
    //MapScreen()
}