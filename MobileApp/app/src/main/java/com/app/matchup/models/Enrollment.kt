package com.app.matchup.models

import java.util.Date
import java.util.UUID

data class Enrollment(
    val id: UUID = UUID.randomUUID(),
    val user: User,
    val event: Event,
    val createdAt: Date = Date()
)