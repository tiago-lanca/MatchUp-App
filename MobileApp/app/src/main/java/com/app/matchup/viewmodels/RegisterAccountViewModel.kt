package com.app.matchup.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.extensions.isValidEmail
import com.app.matchup.models.Country
import com.app.matchup.models.Gender
import com.app.matchup.models.RegisterAccountValidation
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import com.app.matchup.services.UserService
import com.app.matchup.utilities.PasswordEncryption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterAccountViewModel : ViewModel() {
    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    private val _confirmPassword = MutableStateFlow("")
    val confirmPasswordState: StateFlow<String> = _confirmPassword

    private val _validationState = MutableStateFlow(RegisterAccountValidation())
    val validationState: StateFlow<RegisterAccountValidation> = _validationState


    fun onNameChanged(newName: String){
        _user.value = _user.value.copy(name = newName)
        _validationState.value = _validationState.value.copy(nameError = null)
    }

    fun onEmailChanged(newEmail: String){
        _user.value = _user.value.copy(email = newEmail)
        _validationState.value = _validationState.value.copy(emailError = null)
    }

    fun onCountryChanged(newCountry: Country){
        _user.value = _user.value.copy(country = newCountry, mobilePhone = newCountry.phoneCode)
        _validationState.value = _validationState.value.copy(countryError = null)
    }

    fun onCityChanged(newCity: String){
        _user.value = _user.value.copy(city = newCity)
        _validationState.value = _validationState.value.copy(cityError = null)
    }

    fun onMobilePhoneChanged(newMobilePhone: String) {
        _user.value = _user.value.copy(mobilePhone = newMobilePhone)
        _validationState.value = _validationState.value.copy(mobilePhoneError = null)
    }

    fun onPasswordChanged(newPassword: String) {
        _user.value = _user.value.copy(passwordHash = newPassword)
        _validationState.value = _validationState.value.copy(passwordError = null)
    }

    fun onConfirmPasswordChanged(newPassword: String) {
        _confirmPassword.value = newPassword
        _validationState.value = _validationState.value.copy(confirmPasswordError = null)

        if(passwordMatch(_user.value.passwordHash, _confirmPassword.value))
            _validationState.value = _validationState.value.copy(passwordMatchError = null)
    }

    fun onGenderChanged(newGender: String){
        _user.value = _user.value.copy(gender = newGender)
        _validationState.value = _validationState.value.copy(genderError = null)
    }

    fun onFavoriteSportChanged(newFavSport: Sport){
        _user.value = _user.value.copy(favoriteSport = newFavSport)
        _validationState.value = _validationState.value.copy(favSportError = null)
    }


    fun onRegisterNewAccount(newUser: User, context: Context, result: (Boolean) -> Unit) {
        val errors = GetValidationErrors()
        if (errors != null) {
            _validationState.value = errors
            return
        }


        viewModelScope.launch {
            newUser.passwordHash = PasswordEncryption.hashPassword(newUser.passwordHash)
            val success = UserService.CreateUser(newUser)
            result(success)
        }
    }

    private fun GetValidationErrors(): RegisterAccountValidation? {
        val newUser = _user.value

        val errors = RegisterAccountValidation(
            nameError = if (newUser.name.isBlank()) "Name is required." else null,
            emailError = if (newUser.email.isBlank() || !newUser.email.isValidEmail()) "Email is required." else null,
            countryError = if (newUser.country == null) "Country is required." else null,
            cityError = if (newUser.city.isBlank()) "City is required." else null,
            mobilePhoneError = if (newUser.mobilePhone.isBlank()) "Mobile phone is required." else null,
            passwordError = if (newUser.passwordHash.isBlank()) "Password is required." else null,
            confirmPasswordError = if (_confirmPassword.value.isBlank()) "Confirm password is required." else null,
            passwordMatchError = if (!passwordMatch(_user.value.passwordHash, _confirmPassword.value)) "Passwords don't match." else null,
            genderError = if (newUser.gender.isBlank()) "Gender is required." else null,
        )

        return if(errors == RegisterAccountValidation()) null else errors
    }

    private fun passwordMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}