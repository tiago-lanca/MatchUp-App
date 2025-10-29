package com.app.matchup.extensions

import com.app.matchup.utilities.AppConstants
import com.app.matchup.utilities.AppConstants.MAP_DISPLAY_OFFSET
import com.google.android.gms.maps.model.LatLng

fun LatLng.toMapDisplay() = LatLng(
    AppConstants.SeixalCoords.latitude - MAP_DISPLAY_OFFSET,
    AppConstants.SeixalCoords.longitude
)