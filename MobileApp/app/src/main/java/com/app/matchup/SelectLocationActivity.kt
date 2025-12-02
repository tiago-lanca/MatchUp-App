package com.app.matchup

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.matchup.models.User
import com.app.matchup.ui.components.SelectLocationScreen
import com.app.matchup.ui.theme.MatchUpTheme
import com.google.android.gms.maps.model.LatLng

class SelectLocationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatchUpTheme {
                val myLocation = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("my_location", LatLng::class.java)
                }else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra<LatLng>("my_location")
                }
                SelectLocationScreen(myLocation!!)
            }
        }
    }
}

@Preview
@Composable
fun SelectLocationActivityPreview(){

}
