package com.app.matchup.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
data class Enrollment(
    val id: UUID = UUID.randomUUID(),
    val user: User,
    val event: Event,
    val createdAt: Date = Date()
): Parcelable