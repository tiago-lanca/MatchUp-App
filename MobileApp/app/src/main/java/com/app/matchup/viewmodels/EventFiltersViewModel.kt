package com.app.matchup.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.matchup.models.EventFilter
import com.app.matchup.models.Sport
import com.app.matchup.services.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class EventFiltersViewModel : ViewModel() {
    private val _filters = MutableStateFlow(EventFilter())
    val filters: StateFlow<EventFilter> = _filters

    fun updateSport(sport: Sport, context: Context) {
        _filters.update { it.copy(sport = sport) }
        UserSession.saveFilters(context, _filters.value)
    }

    fun updateGender(gender: String, context: Context) {
        _filters.update { it.copy(gender = gender) }
        UserSession.saveFilters(context, _filters.value)
    }

    fun updateCity(city: String?, context: Context) {
        _filters.update { it.copy(city = city) }
        UserSession.saveFilters(context, _filters.value)
    }

    fun updateMaxMembers(maxMembers: Int, context: Context) {
        _filters.update { it.copy(maxMembers = maxMembers) }
        UserSession.saveFilters(context, _filters.value)
    }

    fun updateOnlyMyEvents(onlyMyEvents: Boolean, context: Context) {
        _filters.update { it.copy(onlyMyEvents = onlyMyEvents) }
        UserSession.saveFilters(context, _filters.value)
    }

    fun updateSingleDate(singleDate: Date?, context: Context){
        _filters.update { it.copy(singleDate = singleDate) }
        UserSession.saveFilters(context, _filters.value)
    }

    fun updateStartDate(startDate: Date?, context: Context){
        _filters.update { it.copy(startDate = startDate) }
        UserSession.saveFilters(context, _filters.value)
    }

    fun updateEndDate(endDate: Date?, context: Context){
        _filters.update { it.copy(endDate = endDate) }
        UserSession.saveFilters(context, _filters.value)
    }

    fun setFilters(filters: EventFilter) { _filters.value = filters }

    fun reset() { _filters.value = EventFilter() }

}