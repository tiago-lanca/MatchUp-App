package com.app.matchup.ui.components.MainMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.utilities.UserSession

@Composable
fun MenuItems(
    isUserLoggedIn: Boolean = false,
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onHomeClick: () -> Unit,
    onMyEventsClick: () -> Unit,
    onSearchEventsClick: () -> Unit,
    onCreateNewEventClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSignOutClick: () -> Unit
){
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (!UserSession.isLoggedIn(context = LocalContext.current)) {
            // Login Item
            MenuItem(
                icon = Icons.AutoMirrored.Filled.Login,
                iconDescription = "Login Icon",
                text = "Login",
                onMenuItemClick = { onLoginClick() }
            )

            // Home Item
            MenuItem(
                icon = Icons.Default.Home,
                iconDescription = "Home Icon",
                text = "Home",
                onMenuItemClick = { onHomeClick() }
            )
        } else {

            // Home Item
            MenuItem(
                icon = Icons.Default.Home,
                iconDescription = "Home Icon",
                text = "Home",
                onMenuItemClick = { onHomeClick() }
            )

            // My Events Item
            MenuItem(
                icon = Icons.Default.Event,
                iconDescription = "My Events Icon",
                text = "My Events",
                onMenuItemClick = { onMyEventsClick() }
            )

            // Search Events Item
            MenuItem(
                icon = Icons.Default.Search,
                iconDescription = "Search Events Icon",
                text = "Search Events",
                onMenuItemClick = { onSearchEventsClick() }
            )

            // Create New Event Item
            MenuItem(
                icon = Icons.Default.AddCircleOutline,
                iconDescription = "Create New Event Icon",
                text = "Create New Event",
                onMenuItemClick = { onCreateNewEventClick() }
            )

            // Profile Item
            MenuItem(
                icon = Icons.Default.Person,
                iconDescription = "Profile Icon",
                text = "Profile",
                onMenuItemClick = { onProfileClick() }
            )

            // Sign Out Item
            MenuItem(
                icon = Icons.AutoMirrored.Filled.Logout,
                iconDescription = "Sign out Icon",
                text = "Sign Out",
                onMenuItemClick = { onSignOutClick() }
            )
        }
    }

}

@Preview
@Composable
fun MenuItemsPreview(){
    MenuItems(
        onHomeClick = {},
        onLoginClick = {},
        onMyEventsClick = {},
        onSearchEventsClick = {},
        onCreateNewEventClick = {},
        onProfileClick = {},
        onSignOutClick = {}
    )
}