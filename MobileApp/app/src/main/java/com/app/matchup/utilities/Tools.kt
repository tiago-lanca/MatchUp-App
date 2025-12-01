package com.app.matchup.utilities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Looper
import android.util.Base64
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.matchup.R
import com.app.matchup.ui.theme.GENDER_FEMALE_COLOR
import com.app.matchup.ui.theme.GENDER_MALE_COLOR
import com.app.matchup.ui.theme.GENDER_MIX_COLOR
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Tools{
    fun moveCameraTo(
        latLng: LatLng,
        zoom: Float = AppConstants.DEFAULT_ZOOM,
        coroutineScope: CoroutineScope,
        cameraPositionState: CameraPositionState
    ) {
        coroutineScope.launch {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    latLng,
                    zoom
                ),
                durationMs = 2000
            )
        }
    }

    fun Activity.navigateTo(activity: Class<*>, closeCurrentActivity: Boolean = false){
        val intent = Intent(this, activity)
        startActivity(intent)
        if(closeCurrentActivity) finish()
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
            else -> null
        }
    }

    fun base64ToBitmap(base64: String): Bitmap? {
        val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun uriToBase64(context: Context, uri: Uri): String?{
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()

            if(bytes != null){
                Base64.encodeToString(bytes, Base64.DEFAULT)
            }else{
                null
            }
        }
        catch (e: Exception){
            null
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        context: Context,
        onLocationResult: (LatLng?) -> Unit
    ){
        val fused = LocationServices.getFusedLocationProviderClient(context)

        fused.lastLocation.addOnSuccessListener { location ->
            if (location != null){
                onLocationResult(
                    LatLng(location.latitude, location.longitude)
                )
            }
            else{
                val request = LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY, 1000
                ).setMaxUpdates(1).build()

                fused.requestLocationUpdates(
                    request,
                    object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            fused.removeLocationUpdates(this)
                            val last = result.lastLocation
                            onLocationResult(
                                last?.let { LatLng(it.latitude, it.longitude) }
                            )
                        }
                    },
                    Looper.getMainLooper()
                )
            }
        }
            .addOnFailureListener { onLocationResult(null) }
    }
}