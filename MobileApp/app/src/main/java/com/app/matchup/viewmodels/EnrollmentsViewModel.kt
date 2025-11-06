package com.app.matchup.viewmodels

import androidx.lifecycle.ViewModel
import com.app.matchup.models.Event
import com.app.matchup.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EnrollmentsViewModel : ViewModel()  {
    private val _event = MutableStateFlow<Event?>(null)
    val event = _event.asStateFlow()

    fun setSelectedEvent(event: Event) {
        _event.value = event
    }

    fun joinEvent(user: User){

    }

    fun leaveEvent(event: Event, user: User){

    }
}