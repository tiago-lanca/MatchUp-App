package com.app.matchup.models

import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var email: String = "",
    var country: Country? = null,
    var city: String = "",
    var mobileCountryCode: String = "",
    var mobilePhone: String = "",
    var passwordHash: String = "",
    var gender: String = "",
    var profilePicture: String? = null,
    var favoriteSport: Sport? = null
)
