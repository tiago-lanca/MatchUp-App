package com.app.matchup.models.CountriesJson

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryPhoneCodeJson(
    val root: String,
    val suffixes: List<String>
): Parcelable