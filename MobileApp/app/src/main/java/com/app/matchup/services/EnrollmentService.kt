package com.app.matchup.services

import com.app.matchup.models.Enrollment
import com.app.matchup.dtos.EnrollmentDTO
import com.app.matchup.models.Event
import com.app.matchup.models.User
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder
import java.util.UUID

class EnrollmentService {
    val _userService = UserService()

    suspend fun GetEnrollments(): List<Enrollment>{
        val (_,_, result) = "${SERVER_ROOT}/api/enrollments"
            .httpGet()
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                val gson = GsonBuilder()
                    .create()

                val enrollmentsDtoList = gson.fromJson(responseBody, Array<EnrollmentDTO>::class.java).toList()

                enrollmentsDtoList.map { dto ->
                    Enrollment(
                        id = dto.id,
                        user = _userService.GetUserById(dto.userId) ?: User.empty(),
                        event = EventService.getEventById(dto.eventId) ?: Event.empty(),
                        createdAt = dto.createdAt
                    )
                }
            },
            failure = {
                emptyList<Enrollment>()
            }
        )
    }

    suspend fun GetEnrollmentsByEventId(eventId: UUID): List<Enrollment>?{
        return try {
            val (_,_, result) = "${SERVER_ROOT}/api/enrollments/event/$eventId"
                .httpGet()
                .awaitStringResponseResult()

            result.fold(
                success = { responseBody ->
                    val gson = GsonBuilder()
                        .create()

                    val enrollmentsDtoList = gson.fromJson(responseBody, Array<EnrollmentDTO>::class.java).toList()

                    enrollmentsDtoList.map { dto ->
                        Enrollment(
                            id = dto.id,
                            user = _userService.GetUserById(dto.userId) ?: User.empty(),
                            event = EventService.getEventById(dto.eventId) ?: Event.empty(),
                            createdAt = dto.createdAt
                        )
                    }
                },
                failure = {
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
