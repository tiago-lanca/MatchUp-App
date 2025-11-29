package com.app.matchup.ui.components.Register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.matchup.R
import com.app.matchup.models.Country
import com.app.matchup.models.RegisterAccountValidation
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import com.app.matchup.services.CountryAPIResponse
import com.app.matchup.services.SportService
import com.app.matchup.ui.components.DropdownMenuGeneric
import com.app.matchup.utilities.Tools

@Composable
fun RegisterForm(
    user: User,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onCountryChanged: (Country) -> Unit,
    onCityChanged: (String) -> Unit,
    onMobilePhoneChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    confirmPassword: String,
    onGenderChanged: (String) -> Unit,
    onSportChanged: (Sport) -> Unit,
    validationState: RegisterAccountValidation
){
    var countries by remember { mutableStateOf<List<Country>>(emptyList()) }

    val genders = listOf<String>(
        "M",
        "F",
        "Mix"
    )
    var sports by remember { mutableStateOf<List<Sport>>(emptyList()) }

    LaunchedEffect(Unit) {
        sports = SportService.getSports()
        countries = CountryAPIResponse.getAllCountries()
    }

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
                onValueChange = { onNameChanged(it) },
                label = { Text(stringResource(R.string.name_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(R.string.name_icon_desc),
                        tint = Color(0xFF1565C0)
                    )
                },
                singleLine = true,
                isError = validationState.nameError != null,
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
                onValueChange = { onEmailChanged(it) },
                label = { Text(stringResource(R.string.email_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = stringResource(R.string.email_icon_desc),
                        tint = Color(0xFF1565C0)
                    )
                },
                singleLine = true,
                isError = validationState.emailError != null,
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
                    onItemSelected = {
                        onCountryChanged(it)

                    },
                    getName = { it.name },
                    leadingIcon = {
                        user.country?.flagIcon?.let { flagIcon ->
                            AsyncImage(
                                model = flagIcon,
                                contentDescription = "Country flag",
                                modifier = Modifier.size(20.dp)

                            )
                        }
                    },
                    composableIcon = {
                        AsyncImage(
                            model = it.flagIcon,
                            contentDescription = stringResource(R.string.country_flag_icon_desc),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    isError = validationState.countryError != null,
                    modifier = Modifier
                        .weight(1.1f)
                )

                // City Field
                OutlinedTextField(
                    value = user.city,
                    onValueChange = { onCityChanged(it) },
                    label = { Text(stringResource(R.string.city_label)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = stringResource(R.string.location_icon_desc),
                            tint = Color(0xFF1565C0)
                        )
                    },
                    singleLine = true,
                    isError = validationState.cityError != null,
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
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() || it == '+' } ) {
                        onMobilePhoneChanged(newValue)
                    }
                },
                label = { Text(stringResource(R.string.mobile_phone_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = stringResource(R.string.mobile_phone_icon_desc),
                        tint = Color(0xFF1565C0)
                    )
                },
                singleLine = true,
                isError = validationState.mobilePhoneError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
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
                onValueChange = { onPasswordChanged(it) },
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
                isError = validationState.passwordError != null || validationState.passwordMatchError != null,
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
                onValueChange = { onConfirmPasswordChanged(it) },
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
                isError = validationState.confirmPasswordError != null || validationState.passwordMatchError != null,
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
                items = genders,
                labelColor = Color.Gray,
                selectedItem = user.gender,
                onItemSelected = { onGenderChanged(it) },
                leadingIcon = { Tools.GetGenderIcon(user.gender) },
                getName = { it },
                composableIcon = { Tools.GetGenderIcon(it) },
                isError = validationState.genderError != null,
            )

            // Favorite Sport Field
            DropdownMenuGeneric(
                label = "Sport",
                labelColor = Color.Gray,
                items = sports,
                selectedItem = user.favoriteSport,
                onItemSelected = { onSportChanged(it) },
                leadingIcon = {
                    user.favoriteSport?.icon?.let { sportIcon ->
                        Box(
                            modifier = Modifier.padding(start = 4.dp, end = 0.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(sportIcon),
                                contentDescription = "Sport icon",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                },
                getName = { it.name },
                intIcon = { it.icon!! },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterFormPreview(){
    //RegisterForm()
}