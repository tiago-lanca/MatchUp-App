package com.app.matchup.dtos

import com.app.matchup.enums.Status
import com.app.matchup.models.Address
import com.app.matchup.models.Enrollment
import com.app.matchup.models.Sport
import java.util.Date
import java.util.UUID

data class EventDTO (
    val id: UUID,
    val name: String,
    val address: Address,
    val date: Date,
    val cost: Double,
    val duration: Int,
    val sport: Sport,
    val gender: String,
    val adminId: UUID,
    val adminName: String,
    val maxMembers: Int,
    val notes: String,
    val status: Status,
    val enrollments: List<EnrollmentDTO>
)
