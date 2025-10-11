package com.app.matchup.ui.components.Register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.matchup.viewmodels.RegisterViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.models.Sport
import com.app.matchup.R

@Composable
fun RegisterForm(viewModel: RegisterViewModel = viewModel()){

    val sports = listOf<Sport>(
        Sport(
            name = "Football",
            icon = R.drawable.football_icon
        ),
        Sport(
            name = "Futsal",
            icon = R.drawable.football_icon
        ),
        Sport(
            name = "Corrida",
            icon = R.drawable.football_icon
        )
    )

    val user by viewModel.user.collectAsState()
    var countryDropDownExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Name Field
            OutlinedTextField(
                value = user.name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = { Text("Name") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Email Icon",
                        tint = Color(0xFF1565C0)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color(0xFF1565C0),
                    focusedLabelColor = Color(0xFF1565C0),
                    unfocusedLabelColor = Color.Gray
                )
            )

            // Email Field
            OutlinedTextField(
                value = user.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Email Icon",
                        tint = Color(0xFF1565C0)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color(0xFF1565C0),
                    focusedLabelColor = Color(0xFF1565C0),
                    unfocusedLabelColor = Color.Gray
                )
            )

            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                Box (
                    modifier = Modifier
                        .weight(1f)
                ){
                    // Country Field
                    OutlinedTextField(
                        value = user.favoriteSport?.name ?: "",
                        onValueChange = {},
                        label = { Text("Favorite Sport") },
                        readOnly = true,
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            cursorColor = Color(0xFF1565C0),
                            focusedLabelColor = Color(0xFF1565C0),
                            unfocusedLabelColor = Color.Gray
                        )
                    )

                    DropdownMenu(
                        expanded = countryDropDownExpanded,
                        onDismissRequest = { countryDropDownExpanded = false }
                    ) {
                        sports.forEach { sport ->
                            DropdownMenuItem(
                                text = { Text(sport.name) },
                                onClick = {
                                    viewModel.onFavoriteSportChanged(sport)
                                    countryDropDownExpanded = false
                                }
                            )
                        }
                    }
                }

                // City Field
                OutlinedTextField(
                    value = user.name,
                    onValueChange = { viewModel.onNameChanged(it) },
                    label = { Text("City") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = "Email Icon",
                            tint = Color(0xFF1565C0)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        cursorColor = Color(0xFF1565C0),
                        focusedLabelColor = Color(0xFF1565C0),
                        unfocusedLabelColor = Color.Gray
                    )
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterFormPreview(){
    RegisterForm()
}