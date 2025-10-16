package com.app.matchup.ui.components

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.app.matchup.ui.theme.LOCATION_ICON_COLOR
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch


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

    val context = LocalContext.current
    // For async task
    val coroutineScope = rememberCoroutineScope()

    var hasLocationPermission by remember { mutableStateOf(false) }

    // The launcher to ask for permission of real location
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasLocationPermission = granted }
    )

    // Verify and asks for permission to access real location
    LaunchedEffect(Unit) {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineLocationGranted) {
            hasLocationPermission = true
        } else {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Scaffold { innerPadding ->

        Box(modifier = Modifier.fillMaxSize())
        {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = { mapLoaded = true },
                properties = MapProperties(
                    minZoomPreference = 10f,
                    mapType = mapType
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = true
                )
            )

            // Black gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 1f),
                                Color.Transparent
                            )
                        )
                    )
            )

            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        // Moves to my location defined by SeixalCoords
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngZoom(seixalCoords, defaultZoom)
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(innerPadding),
                containerColor = Color(0xFF006400),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.MyLocation, contentDescription = "My location icon")
            }

            // Button to change the map type Hybrid or Normal
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

