package com.app.matchup.services

import com.app.matchup.models.Address
import com.app.matchup.models.GeocodeJsonResponse
import com.google.android.gms.maps.model.LatLng
import com.app.matchup.utilities.AppConstants.MAPS_API_KEY
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder
import java.util.UUID

object LocationService {

    suspend fun getLocationData(latLng: LatLng): Address?{
        val preferredAddressTypes = listOf("street_address", "route", "premise")

        val (_,_, result) = "https://maps.googleapis.com/maps/api/geocode/json?latlng=${latLng.latitude},${latLng.longitude}&key=${MAPS_API_KEY}"
            .httpGet()
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                val gson = GsonBuilder().create()
                val response = gson.fromJson(responseBody, GeocodeJsonResponse::class.java)
                val result = response.results.firstOrNull() {
                    it.types.any { it in preferredAddressTypes }
                } ?: response.results.firstOrNull()

                val addressResult = result?.address_components

                val streetName = addressResult?.find { "route" in it.types }?.long_name
                val streetNumber = addressResult?.find { "street_number" in it.types }?.long_name
                val formattedStreet = "${streetName} ${streetNumber}"

                Address(
                    id = UUID.randomUUID(),
                    street = formattedStreet,
                    city = addressResult?.find { "locality" in it.types }?.long_name ?: "",
                    zipCode = addressResult?.find { "postal_code" in it.types }?.long_name ?: "",
                    latitude = latLng.latitude,
                    longitude = latLng.longitude
                )

            },
            failure = {
                println("Error getting location data")
                null
            }
        )
    }
}