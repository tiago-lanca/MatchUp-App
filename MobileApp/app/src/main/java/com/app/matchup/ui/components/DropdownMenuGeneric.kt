package com.app.matchup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.app.matchup.models.Country
import com.app.matchup.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T>  DropdownMenuGeneric(
    label: String,
    labelColor: Color = Color.DarkGray,
    items: List<T>,
    selectedItem: T?,
    backgroundColor: Color = Color.White,
    onItemSelected: (T) -> Unit,
    getName: (T) -> String,
    intIcon: ((T) -> Int)? = null,
    composableIcon: (@Composable (T) -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: String? = null,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }

    Box (
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
    ) {
        OutlinedTextField(
            value = selectedItem?.let(getName) ?: "",
            onValueChange = { },
            label = {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = if(isError) Color.Red else labelColor
                )
            },
            readOnly = true,
            singleLine = true,
            leadingIcon = leadingIcon,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Arrow")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp),
            shape = RoundedCornerShape(5.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if(isError) Color.Red else Color(0xFF2C85FF),
                unfocusedBorderColor = if(isError) Color.Red else Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            ),
            isError = isError,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 15.sp,
                lineHeight = 2.sp
            ),
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
                            when {
                                composableIcon != null -> composableIcon(item)

                                intIcon != null -> {
                                    Icon(
                                        painterResource(intIcon(item)),
                                        contentDescription = "Sport icon",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.Unspecified
                                    )
                                }
                            }


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

        if(isError && !errorText.isNullOrBlank()){
            Text(
                text = errorText,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
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
                Box(
                    modifier = Modifier.padding(start = 4.dp, end = 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.football_icon),
                        contentDescription = "Sport icon",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                }
            },
            modifier = Modifier
        )
    }
}