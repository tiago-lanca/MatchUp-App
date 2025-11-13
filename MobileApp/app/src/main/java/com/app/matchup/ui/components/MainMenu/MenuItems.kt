package com.app.matchup.ui.components.MainMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.matchup.R

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

        if (!isUserLoggedIn) {
            // Login Item
            MenuItem(
                icon = Icons.AutoMirrored.Filled.Login,
                iconDescription = stringResource(R.string.login_icon_desc),
                text = stringResource(R.string.login_label),
                onMenuItemClick = { onLoginClick() }
            )

            // Home Item
            MenuItem(
                icon = Icons.Default.Home,
                iconDescription = stringResource(R.string.home_icon_desc),
                text = stringResource(R.string.home_label),
                onMenuItemClick = { onHomeClick() }
            )
        } else {

            // Home Item
            MenuItem(
                icon = Icons.Default.Home,
                iconDescription = stringResource(R.string.home_icon_desc),
                text = stringResource(R.string.home_label),
                onMenuItemClick = { onHomeClick() }
            )

            // My Events Item
            MenuItem(
                icon = Icons.Default.Event,
                iconDescription = stringResource(R.string.my_events_icon_desc),
                text = stringResource(R.string.my_events_label),
                onMenuItemClick = { onMyEventsClick() }
            )

            // Search Events Item
            MenuItem(
                icon = Icons.Default.Search,
                iconDescription = stringResource(R.string.search_events_icon_desc),
                text = stringResource(R.string.search_events_label),
                onMenuItemClick = { onSearchEventsClick() }
            )

            // Create New Event Item
            MenuItem(
                icon = Icons.Default.AddCircleOutline,
                iconDescription =stringResource(R.string.create_new_event_icon_desc),
                text = stringResource(R.string.create_new_event_label),
                onMenuItemClick = { onCreateNewEventClick() }
            )

            // Profile Item
            MenuItem(
                icon = Icons.Default.Person,
                iconDescription = stringResource(R.string.profile_icon_desc),
                text = stringResource(R.string.profile_label),
                onMenuItemClick = { onProfileClick() }
            )

            // Sign Out Item
            MenuItem(
                icon = Icons.AutoMirrored.Filled.Logout,
                iconDescription = stringResource(R.string.signout_icon_desc),
                text = stringResource(R.string.signout_label),
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