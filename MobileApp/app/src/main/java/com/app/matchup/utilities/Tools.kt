package com.app.matchup.utilities

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.matchup.R
import com.app.matchup.models.Gender
import com.app.matchup.ui.theme.GENDER_FEMALE_COLOR
import com.app.matchup.ui.theme.GENDER_MALE_COLOR
import com.app.matchup.ui.theme.GENDER_MIX_COLOR
import com.app.matchup.utilities.AppConstants.MAP_DISPLAY_OFFSET
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
                durationMs = 2000
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

    @Composable
    fun GetGenderIcon(gender: String){
        when (gender) {
            "M" -> Icon(
                imageVector = Icons.Filled.Male,
                contentDescription = "Male Gender Icon",
                tint = GENDER_MALE_COLOR
            )
            "F" -> Icon(
                imageVector = Icons.Filled.Female,
                contentDescription = "Female Gender Icon",
                tint = GENDER_FEMALE_COLOR
            )
            "Mix" -> Icon(
                painterResource(R.drawable.mix_gender),
                contentDescription = "Mix Gender Icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }
    }

}