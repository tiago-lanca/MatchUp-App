package com.app.matchup.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Address(
    val id: UUID,
    val street: String,
    val city: String,
    val zipCode: String,
    val latitude: Double? = null,
    val longitude: Double? = null
): Parcelable
{
    companion object {
        fun empty() = Address(UUID.randomUUID(), "", "", "")
    }
}
