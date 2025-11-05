package com.app.matchup.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.models.Event
import com.app.matchup.services.EventService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent: StateFlow<Event?> = _selectedEvent

    init{
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            val eventList = EventService.getEvents()
            _events.value = eventList
        }
    }

    fun selectEvent(event: Event?){
        _selectedEvent.value = event
    }
}