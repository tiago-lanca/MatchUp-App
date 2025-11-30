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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.app.matchup.models.User
import com.app.matchup.ui.components.Report.ReportScreen
import com.app.matchup.ui.theme.MatchUpTheme

class ReportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatchUpTheme {
                val context = LocalContext.current

                val currentUser = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("current_user", User::class.java)
                }else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra<User>("current_user")
                }

                ReportScreen(context, currentUser)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    MatchUpTheme {
        ReportScreen(LocalContext.current, User())
    }
}