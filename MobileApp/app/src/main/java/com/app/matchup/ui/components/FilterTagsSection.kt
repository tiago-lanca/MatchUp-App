package com.app.matchup.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.models.EventFilter
import com.app.matchup.models.Sport
import com.app.matchup.viewmodels.EventFiltersViewModel
import com.app.matchup.viewmodels.EventsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun FilterTagsSection(
    filters: EventFilter,
    filtersVM: EventFiltersViewModel = viewModel(),
    eventsVM: EventsViewModel = viewModel(),
    context: Context,
    onFilterRemoved: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ){
        if(filters.onlyMyEvents){
            FilterTag(
                text = "My Events",
                onRemoveFilterClick = {
                    filtersVM.updateOnlyMyEvents(false)
                    onFilterRemoved()
                }
            )
        }

        if(filters.gender != "Any") {
            FilterTag(
                text = filters.gender!!,
                onRemoveFilterClick = {
                    filtersVM.updateGender("Any")
                    onFilterRemoved()
                }
            )
        }

        if(filters.sport?.name != "Any") {
            FilterTag(
                icon = filters.sport?.icon!!,
                onRemoveFilterClick = {
                    filtersVM.updateSport(Sport(name = "Any"))
                    onFilterRemoved()
                }
            )
        }

        if(!filters.city.isNullOrBlank()) {
            FilterTag(
                text = filters.city,
                onRemoveFilterClick = {
                    filtersVM.updateCity(null)
                    onFilterRemoved()
                }
            )
        }

        if(filters.startDate != null && filters.endDate != null) {
            val dateFormatter = SimpleDateFormat("dd MMM", Locale.getDefault())
            val dateRangeText =
                if(filters.startDate == filters.endDate) dateFormatter.format(filters.startDate)
                else "${dateFormatter.format(filters.startDate)} - ${dateFormatter.format(filters.endDate)}"

            FilterTag(
                text = dateRangeText,
                onRemoveFilterClick = {
                    filtersVM.updateStartDate(null)
                    filtersVM.updateEndDate(null)
                    onFilterRemoved()
                }
            )
        }


    }
}