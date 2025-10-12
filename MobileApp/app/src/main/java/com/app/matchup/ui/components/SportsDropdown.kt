package com.app.matchup.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.app.matchup.models.Sport


@Composable
fun SportsDropdown(
    selectedSport: Sport?,
    onSportSelected: (Sport) -> Unit,
    sports: List<Sport>,
    modifier: Modifier = Modifier
) {
    DropdownMenuGeneric(
        label = "Sport",
        items = sports,
        selectedItem = selectedSport,
        onItemSelected = onSportSelected,
        getName = { it.name },
        leadingIcon = {
            selectedSport?.icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = "Flag",
                    tint = Color.Unspecified
                )
            }
        },
        modifier = modifier
    )
}