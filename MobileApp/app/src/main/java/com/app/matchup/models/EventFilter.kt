package com.app.matchup.models

import java.util.Date

data class EventFilter(
    val sport: Sport? = Sport(name = "Any"),
    val gender: String? = "Any",
    val city: String? = null,
    val maxMembers: Int? = null,
    val onlyMyEvents: Boolean = false,
    val singleDate: Date? = null,
    val startDate: Date? = null,
    val endDate: Date? = null
)
