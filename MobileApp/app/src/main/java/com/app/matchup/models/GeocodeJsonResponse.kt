package com.app.matchup.models

data class GeocodeJsonResponse (
    val results: List<GeocodeResult>,
    val status: String
)