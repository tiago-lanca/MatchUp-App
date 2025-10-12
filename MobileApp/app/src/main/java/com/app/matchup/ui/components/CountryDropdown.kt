package com.app.matchup.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.app.matchup.models.Country


@Composable
fun CountryDropdown(
    selectedCountry: Country?,
    onCountrySelected: (Country) -> Unit,
    countries: List<Country>,
    modifier: Modifier = Modifier
) {
    DropdownMenuGeneric(
        label = "Country",
        items = countries,
        selectedItem = selectedCountry,
        onItemSelected = onCountrySelected,
        getName = { it.name },
        leadingIcon = {
            selectedCountry?.flagIcon?.let {
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