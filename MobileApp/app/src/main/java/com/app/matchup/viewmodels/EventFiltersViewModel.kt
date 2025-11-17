package com.app.matchup.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.matchup.models.EventFilter
import com.app.matchup.models.Sport
import com.app.matchup.services.UserSession
import com.app.matchup.utilities.EventFilterSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class EventFiltersViewModel : ViewModel() {
    private val _filters = MutableStateFlow(EventFilter())
    val filters: StateFlow<EventFilter> = _filters

    fun updateSport(sport: Sport) {
        _filters.update { it.copy(sport = sport) }
        EventFilterSession.filters = _filters.value
    }

    fun updateGender(gender: String) {
        _filters.update { it.copy(gender = gender) }
        EventFilterSession.filters = _filters.value
    }

    fun updateCity(city: String?) {
        _filters.update { it.copy(city = city) }
        EventFilterSession.filters = _filters.value
    }

    fun updateMaxMembers(maxMembers: Int) {
        _filters.update { it.copy(maxMembers = maxMembers) }
        EventFilterSession.filters = _filters.value
    }

    fun updateOnlyMyEvents(onlyMyEvents: Boolean) {
        _filters.update { it.copy(onlyMyEvents = onlyMyEvents) }
        EventFilterSession.filters = _filters.value
    }

    fun updateSingleDate(singleDate: Date?){
        _filters.update { it.copy(singleDate = singleDate) }
        EventFilterSession.filters = _filters.value
    }

    fun updateStartDate(startDate: Date?){
        _filters.update { it.copy(startDate = startDate) }
        EventFilterSession.filters = _filters.value
    }

    fun updateEndDate(endDate: Date?){
        _filters.update { it.copy(endDate = endDate) }
        EventFilterSession.filters = _filters.value
    }

    fun setFilters(filters: EventFilter) { _filters.value = filters }

    fun reset() {
        _filters.value = EventFilter()
        EventFilterSession.filters = _filters.value
    }

}