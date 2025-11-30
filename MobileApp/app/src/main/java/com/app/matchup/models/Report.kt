package com.app.matchup.models

import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

data class Report(
    val id: UUID = UUID.randomUUID(),
    val user: User? = null,
    val description: String,
    val date: Date = Date()
)
