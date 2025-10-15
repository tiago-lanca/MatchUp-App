package com.app.matchup.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.models.Address
import com.app.matchup.models.Country
import com.app.matchup.models.Event
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class CreateEventViewModel : ViewModel() {
    private val _event = MutableStateFlow(Event())
    val event: StateFlow<Event> = _event

    private val _costInput = MutableStateFlow("0")
    val costInput: StateFlow<String> = _costInput


    fun onNameChanged(newName: String) {
        _event.value = _event.value.copy(name = newName)
    }

    fun onDateChanged(newDate: Date) {
        _event.value = _event.value.copy(date = newDate)
    }

    fun onAddressChanged(newAddress: Address){
        _event.value = _event.value.copy(address = newAddress)
    }

    fun onCostChanged(input: String) {
        // Allows inputs as "3." and accepts 1 decimal max
        if (input.matches(Regex("^\\d*(\\.\\d{0,1})?$"))) {
            _costInput.value = input

            // Check if the input is a valid double, otherwise set it as null and then as 0.0
            val parsed = input.toDoubleOrNull()
            _event.value = _event.value.copy(cost = parsed ?: 0.0)
        }
    }

    fun onDurationChanged(newDuration: Int) {
        _event.value = _event.value.copy(duration = newDuration)
    }

    fun onGenderChanged(newGender: String) {
        _event.value = _event.value.copy(gender = newGender)
    }

    fun onSportChanged(newSport: Sport) {
        _event.value = _event.value.copy(sport = newSport)
    }

    fun onMaxMembersChanged(newMaxMembers: Int){
        _event.value = _event.value.copy(maxMembers = newMaxMembers)
    }

    fun onNotesChanged(newNotes: String) {
        _event.value = _event.value.copy(notes = newNotes)
    }

    fun onCreateEvent() {
        viewModelScope.launch {
            val current = _event.value
            println("Saving event: ${current.name} (${current.address?.city})")
            // TODO: call my API
        }
    }
}