package com.app.matchup.dtos

import java.util.Date
import java.util.UUID

data class EnrollmentDTO (
    val id: UUID,
    val userId: UUID,
    val userName: String,
    val eventId: UUID,
    val eventName: String,
    val createdAt: Date
)
