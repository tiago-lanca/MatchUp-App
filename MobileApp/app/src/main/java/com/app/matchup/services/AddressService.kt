package com.app.matchup.services

import com.app.matchup.models.Address
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpPost
import com.google.gson.GsonBuilder

object AddressService {
    suspend fun createAddress(address: Address): Address? {
        val gson = GsonBuilder()
            .create()

        val bodyJson = gson.toJson(address)

        val (_,_, result) = "${SERVER_ROOT}/api/addresses"
            .httpPost()
            .header("Content-Type" to "application/json")
            .body(bodyJson)
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                println("New address was created.")
                gson.fromJson(responseBody, Address::class.java)
            },
            failure = {
                println("Error creating new address.")
                null
            }
        )
    }
}