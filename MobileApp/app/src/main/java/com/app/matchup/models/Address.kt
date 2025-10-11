package com.app.matchup.models

import java.util.UUID

data class Address(val id: UUID, val street: String, val zipCode: String, val city: String)
