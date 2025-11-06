package com.app.matchup.services

import com.app.matchup.dtos.UserDTO
import com.app.matchup.models.User
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet
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
                    val userDtoList = gson.fromJson(responseBody, Array<UserDTO>::class.java).toList()

                    userDtoList.map { dto ->
                        User(
                            id = dto.id,
                            name = dto.name,
                            email = dto.email,
                            country = dto.country,
                            city = dto.city,
                            mobilePhone = dto.mobilePhone,
                            passwordHash = dto.passwordHash,
                            gender = dto.gender,
                            profilePicture = dto.profilePicture,
                            favoriteSport = dto.favoriteSport
                        )
                    }
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
                    val userDto = gson.fromJson(responseBody, UserDTO::class.java)

                    User(
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
                    )
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
}