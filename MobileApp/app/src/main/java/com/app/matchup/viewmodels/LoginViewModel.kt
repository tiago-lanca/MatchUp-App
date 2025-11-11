package com.app.matchup.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.services.AuthService
import com.app.matchup.utilities.PasswordEncryption
import com.app.matchup.utilities.UserSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    fun onEmailChanged(newEmail: String){
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String){
        _password.value = newPassword
    }

    fun onLoginClicked(context: Context) {

        viewModelScope.launch {
            _isLoading.value = true
            val passwordHash = PasswordEncryption.hashPassword(_password.value)

            val loggedUser = AuthService.proceedLogin(_email.value, passwordHash)

            if(loggedUser != null) {
                _loginSuccess.value = true
                UserSession.saveUserSession(context, loggedUser)

                delay(5000)
            } else {
                _error.value = "Invalid credentials"
            }

            _isLoading.value = false
        }
    }

}