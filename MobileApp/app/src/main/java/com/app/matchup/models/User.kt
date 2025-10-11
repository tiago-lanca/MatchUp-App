package com.app.matchup.models

import java.util.UUID

data class User(
    val id: UUID,
    var name: String,
    var email: String,
    var passwordHash: String,
    var profilePicture: String? = null,
    var favoriteSport: Sport? = null
)
