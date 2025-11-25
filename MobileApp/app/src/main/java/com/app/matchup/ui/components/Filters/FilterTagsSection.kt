package com.app.matchup.ui.components.Filters

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        if (filters.onlyMyEvents) {
            FilterTag(
                text = "My Events",
                onRemoveFilterClick = {
                    filtersVM.updateOnlyMyEvents(false)
                    onFilterRemoved()
                }
            )
        }

        if (filters.gender != "Any") {
            val color = when (filters.gender) {
                "M" -> Color(0xFF78ACFF).copy(alpha = 0.8f)
                "F" -> Color(0xFFEE4DEE)
                "Mix" -> Color.Yellow
                else -> Color.White
            }
            FilterTag(
                text = filters.gender!!,
                backgroundColor = color,
                onRemoveFilterClick = {
                    filtersVM.updateGender("Any")
                    onFilterRemoved()
                }
            )
        }

        if (filters.sport?.name != "Any") {
            filters.sport?.icon?.let { icon ->
                FilterTag(
                    icon = icon,
                    onRemoveFilterClick = {
                        filtersVM.updateSport(Sport(name = "Any"))
                        onFilterRemoved()
                    }
                )
            }
        }

        if (!filters.city.isNullOrBlank()) {
            FilterTag(
                text = filters.city,
                onRemoveFilterClick = {
                    filtersVM.updateCity(null)
                    onFilterRemoved()
                }
            )
        }

        if (filters.startDate != null && filters.endDate != null) {
            val dateFormatter = SimpleDateFormat("dd MMM", Locale.getDefault())
            val dateRangeText =
                if (filters.startDate == filters.endDate) dateFormatter.format(filters.startDate)
                else "${dateFormatter.format(filters.startDate)} - ${
                    dateFormatter.format(
                        filters.endDate
                    )
                }"

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