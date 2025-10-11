package com.app.matchup.models

import java.util.UUID

data class Sport (
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val icon: Int? = null
)
