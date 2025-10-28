package com.app.matchup.services

import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder
import java.util.UUID

class UserService {

    fun GetUsers(){
        "${SERVER_ROOT}/api/users".httpGet().response {
            request, response, result ->

            val responseBody = String(response.data)
            val gson = GsonBuilder()
                .create()

            val json = gson.fromJson(responseBody, Array<UserDTO>)
        }
    }

    fun GetUserById(id: UUID){

    }
}