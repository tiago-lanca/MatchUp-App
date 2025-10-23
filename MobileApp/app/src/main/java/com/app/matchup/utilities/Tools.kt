package com.app.matchup.utilities

import android.app.Activity
import android.content.Intent
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Tools{
    const val APP_NAME = "MatchUp"
    const val APP_VERSION = "1.0.0"
    val SeixalCoords =  LatLng(38.621759, -9.105657)
    val defaultZoom = 15f

    fun moveCameraTo(
        latLng: LatLng,
        zoom: Float = defaultZoom,
        coroutineScope: CoroutineScope,
        cameraPositionState: CameraPositionState
    ) {
        coroutineScope.launch {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    latLng,
                    15f
                ),
                durationMs = 1000
            )
        }
    }

    fun Activity.navigateTo(activity: Class<*>){
        val intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }
}