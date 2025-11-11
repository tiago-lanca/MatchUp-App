package com.app.matchup.services

import android.util.Log
import com.app.matchup.dtos.UserDTO
import com.app.matchup.models.User
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.GsonBuilder
import com.app.matchup.utilities.AppConstants.SERVER_ROOT

object AuthService {
    suspend fun proceedLogin(email: String, password: String): User? {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        val user = User(email = email, passwordHash = password)
        val bodyJson = gson.toJson(user)

        val (_,response, result) = "${SERVER_ROOT}/api/auth"
            .httpPost()
            .header("Content-Type" to "application/json")
            .body(bodyJson)
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                Log.d("AuthService", "Login success: ${responseBody}")
                gson.fromJson(responseBody, User::class.java)

                /*User(
                    id = userDto.id,
                    name = userDto.name,
                    email = userDto.email,
                    country = userDto.country,
                    city = userDto.city,
                    mobilePhone = userDto.mobilePhone,
                    passwordHash = userDto.passwordHash,
                    gender = userDto.gender,
                    profilePicture = userDto.profilePicture,
                    favoriteSport = userDto.favoriteSport
                )*/
            },
            failure = {
                Log.d("AuthService:", "Login error: ${response}")
                null
            }
        )
    }
}