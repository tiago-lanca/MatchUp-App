package com.app.matchup.models

data class GeocodeResult (
    val address_components: List<AddressComponents>,
    val types: List<String>
)
