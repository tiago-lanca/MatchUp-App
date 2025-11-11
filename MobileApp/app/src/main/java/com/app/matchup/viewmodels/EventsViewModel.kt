package com.app.matchup.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.models.Event
import com.app.matchup.services.EnrollmentService
import com.app.matchup.services.EventService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class EventsViewModel : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events
    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent: StateFlow<Event?> = _selectedEvent

    private val _numberOfMembers = MutableStateFlow(0)
    val numberOfMembers: StateFlow<Int> = _numberOfMembers

    private val _isUserEnrolled = MutableStateFlow(false)
    val isUserEnrolled: StateFlow<Boolean> = _isUserEnrolled


    init{
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            val eventList = EventService.getEvents()
            _events.value = eventList
        }
    }

    fun deleteEvent(){

    }

    fun getNumberOfMembersEnrolledInCurrentEvent(){
        if(selectedEvent.value != null) {
            viewModelScope.launch {
                val numberEnrollments =
                    EnrollmentService.getEnrollmentsByEventId(selectedEvent.value!!.id)

                _numberOfMembers.value = numberEnrollments ?: 0
            }
        }
    }

    fun getNumberOfEnrollmentsByEvent(event: Event?, result: (Int) -> Unit){
        if(event != null) {
            viewModelScope.launch {
                val numberEnrollments =
                    EnrollmentService.getEnrollmentsByEventId(event.id)

                result(numberEnrollments ?: 0)
            }

        } else result(0)
    }


    fun isUserEnrolled(context: Context, userId: UUID){
        if(selectedEvent.value != null){
            viewModelScope.launch {
                try {
                    _isUserEnrolled.value =
                        EnrollmentService.isUserEnrolled(
                            selectedEvent.value!!.id, userId)
                }
                catch (e: Exception){
                    e.printStackTrace()
                    _isUserEnrolled.value = false
                }
            }
        }
    }

    fun setUserEnrolled(value: Boolean){
        _isUserEnrolled.value = value
    }


    fun selectEvent(event: Event?){
        _selectedEvent.value = event
    }
}