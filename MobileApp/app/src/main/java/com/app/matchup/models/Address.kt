package com.app.matchup.models

import java.util.UUID

data class Address(
    val id: UUID,
    val street: String,
    val city: String,
    val zipCode: String,
    val latitude: Double? = null,
    val longitude: Double? = null
)
