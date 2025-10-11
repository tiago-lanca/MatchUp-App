package com.app.matchup.models

import java.time.LocalDateTime
import java.util.UUID

data class Event(
    val id: UUID,
    var name: String,
    var date: LocalDateTime,
    var address: Address,
    var cost: Double,
    var duration: Int,
    var genre: String,
    var sport: Sport,
    var admin: User,
    var notes: String? = null
)

