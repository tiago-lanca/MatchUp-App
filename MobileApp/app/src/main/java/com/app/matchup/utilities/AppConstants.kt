package com.app.matchup.utilities

import com.google.android.gms.maps.model.LatLng

object AppConstants{
    const val APP_NAME = "MatchUp"
    const val APP_VERSION = "v1.0.0"
    const val MAPS_API_KEY = "AIzaSyDNRvYuhIBvHH0vhRRBJkPZUiLAh3eoCxc"
    const val MAP_DISPLAY_OFFSET = 0.003
    //"http://95.94.160.172:8081"
    //"http://10.0.2.2:8081"
    const val SERVER_ROOT = "http://10.0.2.2:8081"
    const val USE_REAL_LOCATION = false
    val SeixalCoords = LatLng(38.621759, -9.105657)
    val IadeCoords = LatLng(38.782353, -9.102726)
    const val DEFAULT_ZOOM = 15f
    const val EVENT_ZOOMED = 17f

}
