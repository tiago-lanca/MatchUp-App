package com.app.matchup.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.matchup.models.Address
import com.app.matchup.models.Country
import com.app.matchup.models.CreateEventValidation
import com.app.matchup.models.Event
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import com.app.matchup.services.AddressService
import com.app.matchup.services.EnrollmentService
import com.app.matchup.services.EventService
import com.app.matchup.ui.components.MainMenu.UserProfileSection
import com.app.matchup.services.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class CreateEventViewModel : ViewModel() {
    private val _event = MutableStateFlow(Event())
    val event: StateFlow<Event> = _event

    private val _maxMembersInput = MutableStateFlow("")
    val membersInput: StateFlow<String> = _maxMembersInput

    private val _costInput = MutableStateFlow("0")
    val costInput: StateFlow<String> = _costInput

    private val _durationInput = MutableStateFlow("")
    val durationInput: StateFlow<String> = _durationInput

    private val _validationState = MutableStateFlow(CreateEventValidation())
    val validationState: StateFlow<CreateEventValidation> = _validationState


    fun onNameChanged(newName: String) {
        _event.value = _event.value.copy(name = newName)
        _validationState.value = _validationState.value.copy(nameError = null)
    }

    fun onDateChanged(newDate: Date) {
        _event.value = _event.value.copy(date = newDate)
        _validationState.value = _validationState.value.copy(dateError = null)
    }

    fun onAddressChanged(newAddress: Address) {
        _event.value = _event.value.copy(address = newAddress)
        _validationState.value = _validationState.value.copy(dateError = null)
    }

    fun onCostChanged(input: String) {
        // Allows inputs as "3." and accepts 1 decimal max
        if (input.matches(Regex("^\\d*(\\.\\d{0,1})?$"))) {
            _costInput.value = input
            _validationState.value = _validationState.value.copy(costError = null)

            // Check if the input is a valid double, otherwise set it as null and then as 0.0
            val parsed = input.toDoubleOrNull()
            _event.value = _event.value.copy(cost = parsed ?: 0.0)
        }
    }

    fun onDurationChanged(newDuration: String) {
        if (newDuration.all { it.isDigit() } || newDuration.isEmpty()) {
            _durationInput.value = newDuration
            _validationState.value = _validationState.value.copy(durationError = null)
        }
    }

    fun onGenderChanged(newGender: String) {
        _event.value = _event.value.copy(gender = newGender)
        _validationState.value = _validationState.value.copy(genderError = null)
    }

    fun onSportChanged(newSport: Sport) {
        _event.value = _event.value.copy(sport = newSport)
        _validationState.value = _validationState.value.copy(sportError = null)
    }

    fun onMaxMembersChanged(newMaxMembers: String) {
        if (newMaxMembers.all { it.isDigit() } || newMaxMembers.isEmpty()) {
            _maxMembersInput.value = newMaxMembers
            _validationState.value = _validationState.value.copy(maxMembersError = null)
        }
    }

    fun onNotesChanged(newNotes: String) {
        _event.value = _event.value.copy(notes = newNotes)
    }

    fun onCreateEvent(context: Context, result: (Boolean) -> Unit) {
        // Verifies if there's any error on the form
        val errors = GetValidationErrors()
        if(errors != null){
            _validationState.value = errors
            return
        }

        viewModelScope.launch {
            try {
                val newEvent = _event.value.copy(
                    maxMembers = _maxMembersInput.value.toIntOrNull() ?: 0,
                    cost = costInput.value.toDoubleOrNull() ?: 0.0,
                    duration = durationInput.value.toIntOrNull() ?: 0
                )
                println(UserSession.getUser(context)?.name)

                // Creating new Address
                val createdAddress = AddressService.createAddress(newEvent.address!!)
                if (createdAddress == null) {
                    println("Error creating new address.")
                    result(false)
                    return@launch
                }

                val eventToCreate = newEvent.copy(address = createdAddress, admin = UserSession.getUser(context))
                //newEvent.sport?.icon = null
                eventToCreate.sport?.icon = null
                // Creating new Event
                val createdEvent = EventService.createNewEvent(eventToCreate)
                if (createdEvent != null) {

                    // Creating enrollment for the admin to the event created
                    val createdEnrollment = EnrollmentService.createEnrollment(createdEvent, UserSession.getUser(context)!!)

                    if(createdEnrollment != null) {
                        _event.value = createdEvent
                        result(true)
                    }
                    else
                        result(false)
                }
                else {
                    println("Error creating new event.")
                    result(false)
                }
            }
            catch (e: Exception) {
                println("Error during event creation: ${e.message}")
            }
        }

    }

    private fun GetValidationErrors(): CreateEventValidation? {
        val newEvent = _event.value

        val errors = CreateEventValidation(
            nameError = if (newEvent.name.isBlank()) "Name is required." else null,
            maxMembersError = if (_maxMembersInput.value == "0" || _maxMembersInput.value.isBlank()) "Max. members is required." else null,
            dateError = if (newEvent.date == null) "Date is required." else null,
            costError = if (_costInput.value.isBlank()) "Cost is required." else null,
            durationError = if (_durationInput.value.isBlank() || _durationInput.value == "0") "Duration is required." else null,
            sportError = if (newEvent.sport == null) "Sport is required." else null,
            genderError = if (newEvent.gender.isBlank()) "Gender is required." else null
        )

        // Verifies if errors are all null, else return the errors
        return if (errors == CreateEventValidation()) null else errors
    }
}
