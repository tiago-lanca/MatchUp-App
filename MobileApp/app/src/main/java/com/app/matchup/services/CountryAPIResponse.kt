package com.app.matchup.services

import com.app.matchup.models.CountriesJson.CountryJsonResponse
import com.app.matchup.models.Country
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder

object CountryAPIResponse {

    suspend fun getAllCountries(): List<Country>{
        val countries = mutableListOf<Country>()

        // Search for european countries only (endpoint)
        val (_, _, result) = "https://restcountries.com/v3.1/region/europe?fields=name,flags,idd"
            .httpGet()
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                val gson = GsonBuilder().create()
                val response = gson.fromJson(responseBody, Array<CountryJsonResponse>::class.java)

                response.map { countryJson ->
                    var code = countryJson.idd.root
                    countryJson.idd.suffixes?.forEach { suffix ->
                        code += suffix
                    }

                    countries.add(
                        Country(
                            name = countryJson.name.common,
                            phoneCode = code,
                            flagIcon = countryJson.flags.png
                        )
                    )
                }

                // Sorts the countries list by name but puts Portugal first
                countries.sortedBy {
                    if(it.name == "Portugal") "0" else it.name
                }

            },
            failure = {
                println("Error getting countries")
                emptyList()
            }
        )
    }
}