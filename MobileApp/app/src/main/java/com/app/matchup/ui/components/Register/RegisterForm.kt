package com.app.matchup.ui.components.Register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.matchup.viewmodels.RegisterViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.models.Sport
import com.app.matchup.R
import com.app.matchup.models.Country
import com.app.matchup.ui.components.DropdownMenuGeneric
import com.app.matchup.ui.theme.GENDER_MALE_COLOR

@Composable
fun RegisterForm(viewModel: RegisterViewModel = viewModel()){
    val countries = listOf<Country>(
        Country(
            name = "Portugal",
            phoneCode = "+351",
            icon = R.drawable.football_icon
        ),
        Country(
            name = "Brasil",
            phoneCode = "+55",
            icon = R.drawable.football_icon

        ),
        Country(
            name = "Estados Unidos",
            phoneCode = "+1",
            icon = R.drawable.football_icon
        )
    )
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
    val confirmPassword = ""

    val maxHeightRowForm = 50.dp
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
                    .fillMaxWidth(),
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
                    .fillMaxWidth(),
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Country Field
                DropdownMenuGeneric(
                    label = "Country",
                    items = countries,
                    selectedItem = user.country,
                    onItemSelected = { viewModel.onCountryChanged(it) },
                    getName = { it.name },
                    leadingIcon = {
                        user.country?.icon?.let { flagIcon ->
                            Icon(
                                painter = painterResource(id = flagIcon),
                                contentDescription = "Flag",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(1.1f)
                )

                // City Field
                OutlinedTextField(
                    value = user.city,
                    onValueChange = { viewModel.onCityChanged(it) },
                    label = { Text("City") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = "Email Icon",
                            tint = Color(0xFF1565C0)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f),
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

            // Mobile Phone Field
            OutlinedTextField(
                value = user.mobilePhone,
                onValueChange = { viewModel.onMobilePhoneChanged(it) },
                label = { Text("Mobile Phone") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = "Phone Icon",
                        tint = Color(0xFF1565C0)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
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

            // Password Field
            OutlinedTextField(
                value = user.passwordHash,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = {
                    Text(
                        text = "Password"
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Password Icon",
                        tint = Color(0xFF1565C0)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color(0xFF1565C0),
                    focusedLabelColor = Color(0xFF1565C0),
                    unfocusedLabelColor = Color.Gray
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            // Confirm Password Field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {  },
                label = {
                    Text(
                        text = "Confirm Password"
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Password Icon",
                        tint = Color(0xFF1565C0)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color(0xFF1565C0),
                    focusedLabelColor = Color(0xFF1565C0),
                    unfocusedLabelColor = Color.Gray
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            // Gender Field
            DropdownMenuGeneric(
                label = "Gender",
                items = listOf("M", "F"),
                selectedItem = user.gender,
                onItemSelected = { viewModel.onGenderChanged(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Male,
                        contentDescription = "Flag",
                        tint = GENDER_MALE_COLOR
                    )
                },
                getName = { it },
                modifier = Modifier
            )

            // Favorite Sport Field
            DropdownMenuGeneric(
                label = "Favorite Sport",
                items = sports,
                selectedItem = user.favoriteSport,
                onItemSelected = { viewModel.onFavoriteSportChanged(it) },
                leadingIcon = {
                    user.favoriteSport?.icon?.let { sportIcon ->
                        Icon(
                            painter = painterResource(sportIcon),
                            contentDescription = "Flag",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified
                        )
                    }
                },
                getName = { it.name },
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterFormPreview(){
    RegisterForm()
}