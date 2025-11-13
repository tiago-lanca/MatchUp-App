package com.app.matchup.ui.components.Register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.R
import com.app.matchup.models.Country
import com.app.matchup.models.Sport
import com.app.matchup.ui.components.DropdownMenuGeneric
import com.app.matchup.ui.theme.GENDER_MALE_COLOR
import com.app.matchup.viewmodels.RegisterViewModel

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

    val user by viewModel.user.collectAsState()

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
                label = { Text(stringResource(R.string.name_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(R.string.name_icon_desc),
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
                label = { Text(stringResource(R.string.email_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = stringResource(R.string.email_icon_desc),
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
                    label = stringResource(R.string.country_label),
                    labelColor = Color.Gray,
                    items = countries,
                    selectedItem = user.country,
                    onItemSelected = { viewModel.onCountryChanged(it) },
                    getName = { it.name },
                    leadingIcon = {
                        user.country?.icon?.let { flagIcon ->
                            Icon(
                                painter = painterResource(id = flagIcon),
                                contentDescription = stringResource(R.string.country_flag_icon_desc),
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
                    label = { Text(stringResource(R.string.city_label)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = stringResource(R.string.location_icon_desc),
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
                label = { Text(stringResource(R.string.mobile_phone_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = stringResource(R.string.mobile_phone_icon_desc),
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
                        text = stringResource(R.string.password_label)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = stringResource(R.string.password_icon_desc),
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
                        text = stringResource(R.string.confirm_password_label)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = stringResource(R.string.password_icon_desc),
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
                label = stringResource(R.string.gender_label),
                labelColor = Color.Gray,
                items = listOf("M", "F"),
                selectedItem = user.gender,
                onItemSelected = { viewModel.onGenderChanged(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Male,
                        contentDescription = stringResource(R.string.gender_icon_desc),
                        tint = GENDER_MALE_COLOR
                    )
                },
                getName = { it },
                modifier = Modifier
            )

            // Favorite Sport Field
            DropdownMenuGeneric(
                label = stringResource(R.string.favorite_sport_label),
                labelColor = Color.Gray,
                items = sports,
                selectedItem = user.favoriteSport,
                onItemSelected = { viewModel.onFavoriteSportChanged(it) },
                leadingIcon = {
                    user.favoriteSport?.icon?.let { sportIcon ->
                        Icon(
                            painter = painterResource(sportIcon),
                            contentDescription = stringResource(R.string.favorite_sport_icon_desc),
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