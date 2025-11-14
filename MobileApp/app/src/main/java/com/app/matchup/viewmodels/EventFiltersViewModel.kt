package com.app.matchup.viewmodels

import androidx.lifecycle.ViewModel
import com.app.matchup.models.EventFilter
import com.app.matchup.models.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class EventFiltersViewModel : ViewModel() {
    private val _filters = MutableStateFlow(EventFilter())
    val filters: StateFlow<EventFilter> = _filters

    fun updateSport(sport: Sport) {
        _filters.update { it.copy(sport = sport) }
    }

    fun updateGender(gender: String) {
        _filters.update { it.copy(gender = gender) }
    }

    fun updateCity(city: String) {
        _filters.update { it.copy(city = city) }
    }

    fun updateMaxMembers(maxMembers: Int) {
        _filters.update { it.copy(maxMembers = maxMembers) }
    }

    fun updateOnlyMyEvents(onlyMyEvents: Boolean) {
        _filters.update { it.copy(onlyMyEvents = onlyMyEvents) }
    }

    fun updateSingleDate(singleDate: Date){
        _filters.update { it.copy(singleDate = singleDate) }
    }

    fun updateStartDate(startDate: Date){
        _filters.update { it.copy(startDate = startDate) }
    }

    fun updateEndDate(endDate: Date){
        _filters.update { it.copy(endDate = endDate) }
    }

    fun updateFilter(filter: EventFilter) { _filters.value = filter }

    fun reset() { _filters.value = EventFilter() }

}