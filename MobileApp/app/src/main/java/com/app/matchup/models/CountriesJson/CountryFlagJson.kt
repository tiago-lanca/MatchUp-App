package com.app.matchup.models.CountriesJson

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryFlagJson(
    val png: String,
    val svg: String
): Parcelable
