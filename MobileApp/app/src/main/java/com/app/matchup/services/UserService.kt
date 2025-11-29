package com.app.matchup.services

import com.app.matchup.models.User
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.google.gson.GsonBuilder
import java.util.UUID

object UserService {

    suspend fun GetUsers(): List<User>? {

        return try{
            val (_,_, result) = "${SERVER_ROOT}/api/users"
                .httpGet()
                .awaitStringResponseResult()

            result.fold(
                success = { responseBody ->
                    val gson = GsonBuilder().create()
                    gson.fromJson(responseBody, Array<User>::class.java).toList()

                },
                failure = { error ->
                    println("Error fetching users: ${error.message}")
                    null
                }
            )
        }
        catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun GetUserById(id: UUID): User? {
        return try {
            val (_,_, result) = "${SERVER_ROOT}/api/users/$id"
                .httpGet()
                .awaitStringResponseResult()

            result.fold(
                success = { responseBody ->
                    val gson = GsonBuilder().create()
                    gson.fromJson(responseBody, User::class.java)

                },
                failure = { error ->
                    println("Error fetching user: ${error.message}")
                    null
                }
            )
        }
        catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun CreateUser(newUser: User): Boolean{
        val bodyJson = GsonBuilder().create().toJson(newUser)

        return try{
            val (_,_, result) = "${SERVER_ROOT}/api/users"
                .httpPost()
                .header("Content-Type" to "application/json")
                .body(bodyJson)
                .awaitStringResponseResult()

            result.fold(
                success = { responseBody ->
                    println("User created successfully!")
                    true
                },
                failure = {
                    println("Error creating user: ${it.message}")
                    false
                }
            )
        }
        catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun UpdateUserProfilePicture(userId: UUID, newProfilePicture: String): Boolean{
        return try{
            val (_,_, result) = "${SERVER_ROOT}/api/users/${userId}/update-image"
                .httpPut()
                .body(newProfilePicture)
                .header("Content-Type" to "application/json")
                .awaitStringResponseResult()

            result.fold(
                success = { true },
                failure = { false }
            )
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            false
        }
    }

    suspend fun UpdateUser(userId: UUID, newUser: User): Boolean{
        val bodyJson = GsonBuilder().create().toJson(newUser)

        return try{
            val (_,_, result) = "${SERVER_ROOT}/api/users/${userId}/update"
                .httpPut()
                .body(bodyJson)
                .header("Content-Type" to "application/json")
                .awaitStringResponseResult()

            result.fold(
                success = { true },
                failure = { false }
            )
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            false
        }
    }

}