package com.app.matchup.utilities

import android.app.Activity
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.matchup.R
import com.app.matchup.ui.theme.GENDER_FEMALE_COLOR
import com.app.matchup.ui.theme.GENDER_MALE_COLOR
import com.app.matchup.ui.theme.GENDER_MIX_COLOR
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Tools{
    fun moveCameraTo(
        latLng: LatLng,
        zoom: Float = AppConstants.defaultZoom,
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

    fun getGenderColor(gender: String): Color{
        return when(gender) {
            "M" -> GENDER_MALE_COLOR
            "F" -> GENDER_FEMALE_COLOR
            "Mix" -> GENDER_MIX_COLOR
            else -> Color.White
        }
    }

    fun Int.getSportIconSize(): Dp {
        return when(this) {
            R.drawable.football_icon -> 25.dp
            R.drawable.padel_icon -> 35.dp
            R.drawable.running_icon -> 35.dp
            R.drawable.futsalball_icon -> 25.dp
            R.drawable.basketball_icon -> 25.dp
            else -> 25.dp
        }
    }
}