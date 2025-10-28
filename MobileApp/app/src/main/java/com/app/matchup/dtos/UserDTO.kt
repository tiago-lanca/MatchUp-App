package com.app.matchup.dtos

import com.app.matchup.models.Country
import com.app.matchup.models.Sport
import java.util.UUID

data class UserDTO (
    val id: UUID,
    val name: String,
    val email: String,
    val passwordHash: String,
    val city: String,
    val mobilePhone: String,
    val gender: String,
    val country: Country,
    val favoriteSport: Sport,
    val enrollments: List<EnrollmentDTO>
)
