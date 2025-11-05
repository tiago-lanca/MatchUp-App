package com.app.matchup.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country (
    val name: String,
    val phoneCode: String,
    val icon: Int? = null
): Parcelable
