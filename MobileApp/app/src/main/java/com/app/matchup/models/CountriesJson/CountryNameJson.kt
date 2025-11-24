package com.app.matchup.models.CountriesJson

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryNameJson(
    val common: String
): Parcelable