package com.app.matchup.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.models.Sport


@Composable
fun GenreDropdown(
    selectedGenre: String?,
    onGenreSelected: (String) -> Unit,
    genres: List<String>,
    modifier: Modifier = Modifier
) {
    DropdownMenuGeneric(
        label = "Genre",
        items = genres,
        selectedItem = selectedGenre,
        onItemSelected = onGenreSelected,
        getName = { it },
        leadingIcon = {
            Text(
                text = "M",
                color = Color(0xFF1E90FF),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 5.dp)
            )
        },
        modifier = modifier
    )
}