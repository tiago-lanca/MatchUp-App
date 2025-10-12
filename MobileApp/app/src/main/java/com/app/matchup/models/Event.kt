package com.app.matchup.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

data class Event(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var date: Date = Date(),
    var address: Address? = null,
    var cost: Double = 0.0,
    var duration: Int = 0,
    var gender: String = "M",
    var sport: Sport? = null,
    var maxMembers: Int = 0,
    var admin: User? = null,
    var notes: String? = null
)

