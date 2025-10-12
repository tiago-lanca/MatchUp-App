package com.app.matchup.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.matchup.models.Address

@Composable
fun textFieldColorsReadOnly() = TextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color.White,
    focusedIndicatorColor = Color.LightGray,
    unfocusedIndicatorColor = Color.LightGray,
    disabledIndicatorColor = Color.LightGray,
    cursorColor = Color.Transparent,
    focusedLabelColor = Color.Gray,
    unfocusedLabelColor = Color.Gray,
    disabledLabelColor = Color.Gray,
    focusedTextColor = Color.Gray,
    unfocusedTextColor = Color.Gray,
    disabledTextColor = Color.Gray
)

@Composable
fun AddressSection(
    address: Address? = null,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Address",
            color = Color.White,
            style = MaterialTheme.typography.titleSmall
        )

        // Street
        TextField(
            value = "R. Adão Manuel Ramos Barata 3",
            onValueChange = {},
            label = { Text("Street") },
            readOnly = true,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColorsReadOnly()
        )

        // City and Zip side by side
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            TextField(
                value = "Moscavide",
                onValueChange = {},
                label = { Text("City") },
                readOnly = true,
                singleLine = true,
                modifier = Modifier.weight(2f),
                colors = textFieldColorsReadOnly()
            )

            TextField(
                value = "1850-150",
                onValueChange = {},
                label = { Text("Zip Code") },
                readOnly = true,
                singleLine = true,
                modifier = Modifier.weight(1f),
                colors = textFieldColorsReadOnly()
            )
        }
    }
}