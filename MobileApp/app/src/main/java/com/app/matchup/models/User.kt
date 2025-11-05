package com.app.matchup.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class User(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var email: String = "",
    var country: Country? = null,
    var city: String = "",
    var mobilePhone: String = "",
    var passwordHash: String = "",
    var gender: String = "",
    var profilePicture: String? = null,
    var favoriteSport: Sport? = null
): Parcelable {
    companion object {
        fun empty() = User()
    }
}
