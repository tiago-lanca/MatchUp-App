package com.app.matchup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T>  DropdownMenuGeneric(
    label: String,
    labelColor: Color = Color.Gray,
    items: List<T>,
    selectedItem: T?,
    backgroundColor: Color = Color.White,
    onItemSelected: (T) -> Unit,
    getName: (T) -> String,
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }

    Box (
        modifier = modifier
            .height(56.dp)
            .background(backgroundColor, shape = RoundedCornerShape(5.dp))
    ) {
        OutlinedTextField(
            value = selectedItem?.let(getName) ?: "",
            onValueChange = { },
            label = {
                Text(
                    text = label,
                    maxLines = 1,
                    fontSize = 16.sp,
                    color = labelColor,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            readOnly = true,
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
                .fillMaxSize(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent, // 🔥 borda fora de foco invisível
                focusedBorderColor = Color.Transparent,   // 🔥 borda focada invisível
                disabledBorderColor = Color.Transparent
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
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
                                modifier = Modifier.size(15.dp)
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