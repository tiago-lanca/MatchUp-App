package com.app.matchup.viewmodels

import androidx.lifecycle.ViewModel
import com.app.matchup.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserProfileViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun loadUser(initialUser: User){
        if(_user.value == null){
            _user.value = initialUser
        }
    }
    fun updateProfilePicture(base64: String){
        _user.value = _user.value?.copy(profilePicture = base64)
    }
}