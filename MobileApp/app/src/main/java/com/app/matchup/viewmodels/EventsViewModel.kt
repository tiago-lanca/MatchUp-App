package com.app.matchup.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.extensions.addDays
import com.app.matchup.extensions.removePastEvents
import com.app.matchup.models.Event
import com.app.matchup.models.EventFilter
import com.app.matchup.services.EnrollmentService
import com.app.matchup.services.EventService
import com.app.matchup.services.UserSession
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    init{
        loadAllEvents()
    }

    fun loadAllEvents(result: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val eventList = EventService.getEvents()
                _events.value = eventList.removePastEvents()
                result(true)
            }
            catch (e: Exception){
                e.printStackTrace()
                result(false)
            }
        }
    }

    fun loadFilteredEvents(filter: EventFilter, context: Context, result: (Boolean) -> Unit = {}) {

        viewModelScope.launch {
            try {
                _isLoading.value = true

                val activeUser = UserSession.getUser(context)
                val allEvents = EventService.getEvents()

                val showableEvents = mutableListOf<Event>()

                allEvents.forEach { event ->
                    val isFull = EnrollmentService.getEnrollmentsByEventId(event.id) == event.maxMembers

                    if(activeUser != null) {
                        val isUserEnrolledInEvent =
                            EnrollmentService.isUserEnrolled(event.id, activeUser?.id!!)

                        // Checks if the event is full and the user is already enrolled in it,
                        // then adds the event to the showable events list
                        if (isFull && isUserEnrolledInEvent)
                            showableEvents.add(event)
                    }

                    if (!isFull) {
                        showableEvents.add(event)
                    }
                }

                _events.value = showableEvents.removePastEvents()

                if (filter.onlyMyEvents) {
                    _events.value = _events.value.filter { it.admin?.id == activeUser?.id }
                }

                if(filter.sport?.name != "Any"){
                    _events.value = _events.value.filter { it.sport?.name == filter.sport?.name }
                }

                if(filter.gender != "Any"){
                    _events.value = _events.value.filter { it.gender == filter.gender }
                }

                if(!filter.city.isNullOrEmpty()){
                    _events.value = _events.value.filter { it.address?.city?.lowercase() == filter.city.lowercase() }
                }

                if(filter.startDate != null){
                    _events.value = _events.value.filter { it.date!! >= filter.startDate && it.date!! <= filter.endDate?.addDays(1) }

                }

                _isLoading.value = false
                result(true)
            }
            catch (e: Exception){
                e.printStackTrace()
                result(false)
            }
        }
    }

    fun deleteEvent(result: (Boolean) -> Unit){
        viewModelScope.launch {
            try {
                result(EventService.deleteEvent(_selectedEvent.value!!.id))
            }
            catch (e: Exception){
                e.printStackTrace()
                result(false)
            }
        }
    }

    fun getNumberOfEnrollmentsOnSelectedEvent(){
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