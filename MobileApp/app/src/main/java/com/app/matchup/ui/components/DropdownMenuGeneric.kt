package com.app.matchup.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.app.matchup.models.Country
import com.app.matchup.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T> DropdownMenuGeneric(
    label: String,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    getName: (T) -> String,
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }

    val countries = listOf(
        Country("Portugal", "+351", R.drawable.football_icon),
        Country("Spain", "+34", R.drawable.football_icon),
        Country("Brazil", "+55", R.drawable.football_icon),
        Country("France", "+33", R.drawable.football_icon)
    )

    Box (modifier = modifier) {
        OutlinedTextField(
            value = selectedItem?.let(getName) ?: "",
            onValueChange = { },
            label = {
                Text(
                    text = "Country",
                    maxLines = 1,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .heightIn(max = 56.dp)
                        .fillMaxWidth()
                )
            },
            leadingIcon = leadingIcon,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Countries Dropdown Arrow"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                cursorColor = Color(0xFF1565C0),
                focusedLabelColor = Color(0xFF1565C0),
                unfocusedLabelColor = Color.Gray
            ),
            textStyle = LocalTextStyle.current.copy(lineHeight = 18.sp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Row (verticalAlignment = Alignment.CenterVertically)
                        {
                            Icon(
                                painter = painterResource(R.drawable.football_icon),
                                contentDescription = "Country Flag",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(25.dp)
                            )
                            Text(
                                item.let { getName(item) },
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                            )
                        }

                    },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownMenuGenericPreview(){
    val countries = listOf(
        Country("Portugal", "+351", R.drawable.football_icon),
        Country("Brazil", "+55", R.drawable.football_icon)
    )

    MaterialTheme {
        DropdownMenuGeneric(
            label = "Country",
            items = countries,
            selectedItem = countries.first(),
            onItemSelected = {},
            getName = { it.name },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.football_icon),
                    contentDescription = "Flag",
                    tint = Color.Unspecified
                )
            },
            modifier = Modifier
        )
    }
}