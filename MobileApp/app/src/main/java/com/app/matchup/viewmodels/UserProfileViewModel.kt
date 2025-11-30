package com.app.matchup.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.extensions.isValidEmail
import com.app.matchup.models.Country
import com.app.matchup.models.AccountValidation
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import com.app.matchup.services.UserService
import com.app.matchup.utilities.PasswordEncryption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() {
    private val _user = MutableStateFlow(User())
    val user: StateFlow<User?> = _user

    private val _newPassword = MutableStateFlow<String?>(null)
    val newPassword: StateFlow<String?> = _newPassword
    private val _currentPassword = MutableStateFlow<String?>(null)
    val currentPassword: StateFlow<String?> = _currentPassword
    private val _validationState = MutableStateFlow(AccountValidation())
    val validationState: StateFlow<AccountValidation> = _validationState

    private val _passwordChangeState = MutableStateFlow(false)
    val passwordChangeState: StateFlow<Boolean> = _passwordChangeState

    private var oldPassword: String = ""


    fun loadUser(initialUser: User) {
        _user.value = initialUser
        oldPassword = initialUser.passwordHash
    }

    fun onNameChange(newName: String) {
        _user.value = _user.value.copy(name = newName)
        _validationState.value = _validationState.value.copy(nameError = null)
    }

    fun onEmailChange(newEmail: String) {
        _user.value = _user.value.copy(email = newEmail)
        _validationState.value = _validationState.value.copy(emailError = null)
    }

    fun onCountryChange(newCountry: Country) {
        if(newCountry != user.value?.country) {
            _user.value.mobilePhone = "${newCountry.phoneCode} "
        }

        _user.value = _user.value.copy(country = newCountry)
        _validationState.value = _validationState.value.copy(countryError = null)
    }

    fun onCityChange(newCity: String) {
        _user.value = _user.value.copy(city = newCity)
        _validationState.value = _validationState.value.copy(cityError = null)
    }

    fun onMobilePhoneChange(newMobilePhone: String) {
        _user.value = _user.value.copy(mobilePhone = newMobilePhone)
        _validationState.value = _validationState.value.copy(mobilePhoneError = null)
    }

    fun onGenderChange(newGender: String) {
        _user.value = _user.value.copy(gender = newGender)
        _validationState.value = _validationState.value.copy(genderError = null)
    }

    fun onFavoriteSportChange(newFavoriteSport: Sport) {
        _user.value = _user.value.copy(favoriteSport = newFavoriteSport)
    }

    fun onPasswordChange(newPassword: String) {
        _currentPassword.value = newPassword
    }

    fun onNewPasswordChange(newConfirmPassword: String) {
        _newPassword.value = newConfirmPassword
    }

    fun onUpdateProfileClick(result: (Boolean) -> Unit){
        val errors = GetValidationErrors()
        if(errors != null) {
            _validationState.value = errors
            return
        }

        viewModelScope.launch {
            // Checks if password is being changed, if so then updates the password hash of the user
            if(_passwordChangeState.value) {
                _user.value = _user.value.copy(passwordHash = PasswordEncryption.hashPassword(_newPassword.value!!))
            }

            try{
                val updated = UserService.UpdateUser(_user.value.id, _user.value)
                result(updated)
            }
            catch (e: Exception){
                e.printStackTrace()
                result(false)
            }
        }
    }
    fun updateProfilePicture(base64: String, result: (Boolean) -> Unit){
        _user.value = _user.value.copy(profilePicture = base64)

        viewModelScope.launch {
            try{
                val updated = UserService.UpdateUserProfilePicture(_user.value.id, base64)
                result(updated)
            }
            catch (e: Exception){
                e.printStackTrace()
                result(false)
            }
        }
    }

    private fun GetValidationErrors(): AccountValidation? {
        var newUser = _user.value
        newUser = newUser.copy(passwordHash = PasswordEncryption.hashPassword(currentPassword.value ?: ""))


        val errors = AccountValidation(
            nameError = if (newUser.name.isBlank()) "Name is required." else null,
            emailError = if (newUser.email.isBlank() || !newUser.email.isValidEmail()) "Email is required." else null,
            countryError = if (newUser.country == null) "Country is required." else null,
            cityError = if (newUser.city.isBlank()) "City is required." else null,
            mobilePhoneError = if (newUser.mobilePhone.isBlank()) "Mobile phone is required." else null,
            passwordError =
                if(_passwordChangeState.value) {
                    if (oldPassword != newUser.passwordHash) "Password is incorrect."
                    else if(newUser.passwordHash.isBlank()) "Password is required."
                    else null
                } else null,
            confirmPasswordError =
                if(_passwordChangeState.value) {
                    if (_newPassword.value.isNullOrBlank())
                        "Confirm password is required."
                    else null
                } else null,
            genderError = if (newUser.gender.isBlank()) "Gender is required." else null,
        )

        return if(errors == AccountValidation()) null else errors
    }

    private fun passwordMatch(password: String?, confirmPassword: String?): Boolean {
        return password == confirmPassword
    }

    fun setPasswordState(state: Boolean){
        _passwordChangeState.value = state
    }

    fun clearPasswords(){
        _newPassword.value = null
        _currentPassword.value = ""
    }

}