package com.app.matchup.ui.components.MainMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuItems(
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Home Item
        MenuItem(
            icon = Icons.Default.Home,
            iconDescription = "Home Icon",
            text = "Home"
        )

        // My Events Item
        MenuItem(
            icon = Icons.Default.Event,
            iconDescription = "My Events Icon",
            text = "My Events"
        )

        // Search Events Item
        MenuItem(
            icon = Icons.Default.Search,
            iconDescription = "Search Events Icon",
            text = "Search Events"
        )

        // Create New Event Item
        MenuItem(
            icon = Icons.Default.AddCircleOutline,
            iconDescription = "Create New Event Icon",
            text = "Create New Event"
        )

        // Profile Item
        MenuItem(
            icon = Icons.Default.Person,
            iconDescription = "Profile Icon",
            text = "Profile"
        )

        // Sign Out Item
        MenuItem(
            icon = Icons.AutoMirrored.Filled.Logout,
            iconDescription = "Sign out Icon",
            text = "Sign Out"
        )
    }

}

@Preview
@Composable
fun MenuItemsPreview(){
    MenuItems()
}