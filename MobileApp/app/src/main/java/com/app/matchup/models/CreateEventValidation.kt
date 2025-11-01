package com.app.matchup.models

data class CreateEventValidation(
    val nameError: String? = null,
    val dateError: String? = null,
    val costError: String? = null,
    val maxMembersError: String? = null,
    val durationError: String? = null,
    val sportError: String? = null,
    val genderError: String? = null
)
