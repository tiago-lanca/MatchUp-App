package com.app.matchup.services

import com.app.matchup.extensions.getSportIcon
import com.app.matchup.models.Sport
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder

object SportService{

    suspend fun getSports(): List<Sport>{
        val (_,_, result) = "${SERVER_ROOT}/api/sports"
            .httpGet()
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                val gson = GsonBuilder()
                    .create()

                val list = gson.fromJson(responseBody, Array<Sport>::class.java).toList()

                // Assign it respective icon according to Sport name
                for(sport in list){
                    sport.icon = sport.getSportIcon()
                }

                return list
            },
            failure = { emptyList() }
        )
    }
}