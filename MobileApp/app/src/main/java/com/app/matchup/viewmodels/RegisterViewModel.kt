package com.app.matchup.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.models.Country
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel(){
    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    fun onNameChanged(newName: String) {
        _user.value = _user.value.copy(name = newName)
    }

    fun onEmailChanged(newEmail: String) {
        _user.value = _user.value.copy(email = newEmail)
    }

    fun onMobilePhoneChanged(newMobilePhone: String) {
        _user.value = _user.value.copy(mobilePhone = newMobilePhone)
    }

    fun onPasswordChanged(newPassword: String) {
        _user.value = _user.value.copy(passwordHash = newPassword)
    }

    fun onFavoriteSportChanged(newFavSport: Sport) {
        _user.value = _user.value.copy(favoriteSport = newFavSport)
    }

    fun onCountryChanged(newCountry: Country) {
        _user.value = _user.value.copy(
            country = newCountry,
            mobileCountryCode = newCountry.phoneCode
        )
    }

    fun onCreateUser() {
        viewModelScope.launch {
            val current = _user.value
            println("Salvando usuário: ${current.name} (${current.email})")
            // TODO: chamar seu repositório / API
        }
    }
}