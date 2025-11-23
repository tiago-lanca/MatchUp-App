package com.app.matchup.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.enums.ShowMyEventsType
import com.app.matchup.enums.Status
import com.app.matchup.models.Event
import com.app.matchup.models.User
import com.app.matchup.services.EventService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class MyEventsViewModel : ViewModel() {
    private val _myEventsList = MutableStateFlow<List<Event>>(emptyList())
    val myEventsList: StateFlow<List<Event>> = _myEventsList
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _myEventSelected = MutableStateFlow<Event?>(null)
    val myEventSelected: StateFlow<Event?> = _myEventSelected

    fun loadMyEvents(eventStatus: ShowMyEventsType, user: User){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                delay(1000)

                val userEvents = EventService.getEventsByEnrolledUserId(user.id)

                val filteredEvents = when(eventStatus){
                    ShowMyEventsType.ACTIVE -> userEvents.filter { it.status == Status.OPEN && it.date!! >= Date() }
                    ShowMyEventsType.COMPLETED -> userEvents.filter { it.date!! < Date() }
                    ShowMyEventsType.ALL -> userEvents
                }
                _myEventsList.value = filteredEvents
                _isLoading.value = false
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun setSelectedEvent(event: Event?){
        _myEventSelected.value = event
    }
}