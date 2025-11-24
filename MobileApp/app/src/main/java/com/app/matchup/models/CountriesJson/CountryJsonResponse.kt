package com.app.matchup.models.CountriesJson

data class CountryJsonResponse(
    val flags: CountryFlagJson,
    val name: CountryNameJson,
    val idd: CountryPhoneCodeJson
)
