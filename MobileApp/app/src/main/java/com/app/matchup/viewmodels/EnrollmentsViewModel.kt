package com.app.matchup.viewmodels

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.models.Event
import com.app.matchup.models.User
import com.app.matchup.services.EnrollmentService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EnrollmentsViewModel : ViewModel()  {
    private val _event = MutableStateFlow<Event?>(null)
    val event = _event.asStateFlow()

    fun setSelectedEvent(event: Event) {
        _event.value = event
        println("ID: ${_event.value?.id}")
        println("Name: ${_event.value?.name}")
    }

    fun joinEvent(user: User, result: (Boolean) -> Unit){
        viewModelScope.launch {
            try
            {
                println("Creating enrollment for user ${user.name}")
                println("On event ${event.value?.name}")

                val createdEnrollment = EnrollmentService.createEnrollment(event.value!!, user)
                if (createdEnrollment != null){
                    result(true)
                }
                else{
                    result(false)
                }
            }
            catch (e: Exception){
                println("Error creating enrollment: ${e.message}")
            }
        }
    }

    fun leaveEvent(user: User, result: (Boolean) -> Unit){
        viewModelScope.launch {
            try {
                val deletedEnrollment = EnrollmentService.deleteEnrollment(event.value?.id!!, user.id)
                if(deletedEnrollment)
                    result(true)
                else
                    result(false)
            }
            catch (e: Exception){
                println("Error deleting enrollment: ${e.message}")
            }
        }
    }
}