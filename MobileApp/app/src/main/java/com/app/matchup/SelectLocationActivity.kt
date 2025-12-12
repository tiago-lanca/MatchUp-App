package com.app.matchup

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.app.matchup.models.User
import com.app.matchup.ui.components.SelectLocationScreen
import com.app.matchup.ui.theme.MatchUpTheme
import com.app.matchup.utilities.AppConstants
import com.app.matchup.utilities.AppConstants.IadeCoords
import com.app.matchup.utilities.AppConstants.SeixalCoords
import com.app.matchup.utilities.Tools.getCurrentLocation
import com.google.android.gms.maps.model.LatLng


class SelectLocationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatchUpTheme {

                val context = LocalContext.current

                var myLocation by remember { mutableStateOf<LatLng?>(null) }

                LaunchedEffect(Unit) {
                    val locationFromIntent =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            intent.getParcelableExtra("my_location", LatLng::class.java)
                        } else {
                            @Suppress("DEPRECATION")
                            intent.getParcelableExtra<LatLng>("my_location")
                        }

                    if(locationFromIntent != null){
                        myLocation = locationFromIntent
                        return@LaunchedEffect
                    }


                    getCurrentLocation(context) { latLng ->
                        myLocation =
                            if (AppConstants.USE_REAL_LOCATION) latLng else IadeCoords

                    }
                }

                if(myLocation != null)
                    SelectLocationScreen(myLocation)
            }
        }
    }
}

@Preview
@Composable
fun SelectLocationActivityPreview(){

}
