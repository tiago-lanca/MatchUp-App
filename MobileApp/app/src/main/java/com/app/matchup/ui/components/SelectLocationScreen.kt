package com.app.matchup.ui.components

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import com.app.matchup.CreateEventActivity
import com.app.matchup.MainActivity
import com.app.matchup.services.GeocodeService
import com.app.matchup.ui.theme.LOCATION_ICON_COLOR
import com.app.matchup.ui.theme.MY_LOCATION_ICON_COLOR
import com.app.matchup.utilities.Tools.navigateTo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun SelectLocationScreen() {
    val seixalCoords = LatLng(38.621759, -9.105657)
    val defaultZoom = 15f

    var mapLoaded by remember { mutableStateOf(false) }
    var mapType by remember { mutableStateOf(MapType.NORMAL) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            seixalCoords, defaultZoom
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    var filterLocation by remember { mutableStateOf("") }

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

    Box(modifier = Modifier.fillMaxSize()) {

        // Google Map
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
        // Map is loading
        if (!mapLoaded) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        } else {

            // Black Gradient Top Sector
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .height(300.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.9f),
                                Color.Black.copy(alpha = 0.8f),
                                Color.Black.copy(alpha = 0.6f),
                                Color.Transparent
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

                Column {
                    // Title Section and Close button
                    Box(Modifier.fillMaxWidth()) {
                        Text(
                            text = "Select a Location",
                            color = Color.White,
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Center)
                        )

                        // Close Button
                        IconButton(
                            onClick = {
                                (context as Activity).navigateTo(
                                    activity = MainActivity::class.java,
                                    closeCurrentActivity = true
                                )
                            },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .background(Color.Red, RoundedCornerShape(50))
                                .size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Search Location Filter
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    if (hasLocationPermission) {
                                        coroutineScope.launch {
                                            cameraPositionState.animate(
                                                update = CameraUpdateFactory.newLatLngZoom(
                                                    seixalCoords,
                                                    defaultZoom
                                                ),
                                                durationMs = 1000
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .background(MY_LOCATION_ICON_COLOR, RoundedCornerShape(50))
                                    .size(42.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MyLocation,
                                    contentDescription = "My Location Icon",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            OutlinedTextField(
                                value = filterLocation,
                                onValueChange = { filterLocation = it },
                                label = { Text("City") },
                                singleLine = true,
                                trailingIcon = {
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(0.dp)
                                    ) {
                                        if (filterLocation.isNotEmpty()) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "Clear text",
                                                    tint = Color.Black,
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                        .padding(0.dp)
                                                        .clickable { filterLocation = ""}
                                                )
                                        }

                                            Icon(
                                                imageVector = Icons.Default.Search,
                                                contentDescription = "Search Icon",
                                                tint = Color(0xFF006400),
                                                modifier = Modifier
                                                    .clickable{
                                                        if (hasLocationPermission) {
                                                            val geocoder =
                                                                Geocoder(context, Locale.getDefault())

                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                                                geocoder.getFromLocationName(
                                                                    filterLocation,
                                                                    1
                                                                ) { addresses ->
                                                                    val address = addresses.firstOrNull()
                                                                    if (address != null) {
                                                                        val latLng = LatLng(
                                                                            address.latitude,
                                                                            address.longitude
                                                                        )
                                                                        coroutineScope.launch {
                                                                            cameraPositionState.animate(
                                                                                CameraUpdateFactory.newLatLngZoom(
                                                                                    latLng,
                                                                                    14f
                                                                                )
                                                                            )
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                coroutineScope.launch {
                                                                    val addresses =
                                                                        geocoder.getFromLocationName(
                                                                            filterLocation,
                                                                            1
                                                                        )
                                                                    val address = addresses?.firstOrNull()
                                                                    if (address != null) {
                                                                        val latLng = LatLng(
                                                                            address.latitude,
                                                                            address.longitude
                                                                        )
                                                                        cameraPositionState.animate(
                                                                            CameraUpdateFactory.newLatLngZoom(
                                                                                latLng,
                                                                                14f
                                                                            ),
                                                                            durationMs = 2000
                                                                        )
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                            )
                                        }

                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color.Black,
                                    unfocusedIndicatorColor = Color.Black,
                                    cursorColor = Color(0xFF006400),
                                    focusedLabelColor = Color.White,
                                    unfocusedLabelColor = Color.Black,
                                    focusedTrailingIconColor = Color(0xFF006400)
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .width(250.dp)
                            )
                        }
                    }
                }
            }

            // Map Marker Location
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Marker",
                tint = LOCATION_ICON_COLOR,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
            )

            // Confirm Button
            Button(
                onClick = {
                    coroutineScope.launch {
                        val result = GeocodeService.getLocationData(selectedPosition)
                        val intent = Intent(context, CreateEventActivity::class.java)
                        intent.putExtra("address", result)
                        context.startActivity(intent)
                    }

                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006400),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "CONFIRM",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp
                )
            }
        }
    }

}

