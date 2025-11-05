package com.app.matchup.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Sport (
    val id: UUID = UUID.randomUUID(),
    val name: String,
    var icon: Int? = null
): Parcelable
