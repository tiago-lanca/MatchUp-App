package com.app.matchup.models

import android.os.Parcelable
import com.app.matchup.models.CountriesJson.CountryFlagJson
import com.app.matchup.models.CountriesJson.CountryNameJson
import com.app.matchup.models.CountriesJson.CountryPhoneCodeJson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country (
    val name: String,
    val phoneCode: String,
    val icon: String
): Parcelable
