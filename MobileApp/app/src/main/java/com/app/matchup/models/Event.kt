package com.app.matchup.models

import android.os.Parcelable
import com.app.matchup.enums.Status
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
data class Event(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var date: Date? = null,
    var address: Address? = null,
    var cost: Double = 0.0,
    var duration: Int = 0,
    var gender: String = "M",
    var sport: Sport? = null,
    var maxMembers: Int = 0,
    var admin: User? = null,
    var notes: String? = null,
    var status: Status = Status.OPEN,
    var enrollments: List<Enrollment> = emptyList<Enrollment>()
): Parcelable {
    companion object{
        fun empty() = Event()
    }
}

