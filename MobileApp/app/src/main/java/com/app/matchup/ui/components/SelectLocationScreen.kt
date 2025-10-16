package com.app.matchup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.ui.theme.LOCATION_ICON_COLOR
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.MapType

@Composable
fun SelectLocationScreen(
    onLocationSelected: (LatLng) -> Unit
) {
    val seixalCoords = LatLng(38.621759, -9.105657)
    val defaultZoom = 15f

    var mapLoaded by remember { mutableStateOf(false) }
    var mapType by remember { mutableStateOf(MapType.NORMAL) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            seixalCoords, defaultZoom
        )
    }

    val selectedPosition by remember {
        derivedStateOf { cameraPositionState.position.target }
    }
    Scaffold { innerPadding ->

        Box(modifier = Modifier.fillMaxSize())
        {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = { mapLoaded = true },
                properties = MapProperties(
                    isMyLocationEnabled = false,
                    minZoomPreference = 10f,
                    mapType = mapType
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = false
                )
            )


            // Botão para alternar tipo de mapa
            Button(
                onClick = {
                    mapType = when (mapType) {
                        MapType.NORMAL -> MapType.HYBRID
                        MapType.HYBRID -> MapType.NORMAL
                        else -> MapType.NORMAL
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(innerPadding),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006400),
                    contentColor = Color.White
                )
            ) {
                Text("Mudar tipo")
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (mapLoaded) {

                // Selected Location Icon
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Marker location to new event",
                    tint = LOCATION_ICON_COLOR,
                    modifier = Modifier
                        .size(40.dp)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /* To show coordinates
                Text(
                    text = "Lat: ${"%.6f".format(selectedPosition.latitude)} | Lng: ${
                        "%.6f".format(
                            selectedPosition.longitude
                        )
                    }",
                )

                Spacer(modifier = Modifier.height(12.dp))*/

                    // Confirm Button
                    Button(
                        onClick = { onLocationSelected(selectedPosition) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF006400),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                    ) {
                        Text(
                            text = "CONFIRM",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 2.sp
                        )
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

