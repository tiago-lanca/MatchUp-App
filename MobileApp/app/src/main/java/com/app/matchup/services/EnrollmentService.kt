package com.app.matchup.services

import com.app.matchup.models.Enrollment
import com.app.matchup.dtos.EnrollmentDTO
import com.app.matchup.models.Event
import com.app.matchup.models.User
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.GsonBuilder
import java.util.UUID

object EnrollmentService {

    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    suspend fun getEnrollments(): List<Enrollment>{
        val (_,_, result) = "${SERVER_ROOT}/api/enrollments"
            .httpGet()
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->

                gson.fromJson(responseBody, Array<Enrollment>::class.java).toList()

                /*enrollmentsDtoList.map { dto ->
                    Enrollment(
                        id = dto.id,
                        user = UserService.GetUserById(dto.userId) ?: User.empty(),
                        event = EventService.getEventById(dto.eventId) ?: Event.empty(),
                        createdAt = dto.createdAt
                    )
                }*/
            },
            failure = {
                emptyList<Enrollment>()
            }
        )
    }

    suspend fun getEnrollmentsByEventId(eventId: UUID): List<Enrollment>?{
        return try {
            val (_,_, result) = "${SERVER_ROOT}/api/enrollments/event/$eventId"
                .httpGet()
                .awaitStringResponseResult()

            result.fold(
                success = { responseBody ->

                    gson.fromJson(responseBody, Array<Enrollment>::class.java).toList()

                    /*enrollmentsDtoList.map { dto ->
                        Enrollment(
                            id = dto.id,
                            user = UserService.GetUserById(dto.userId) ?: User.empty(),
                            event = EventService.getEventById(dto.eventId) ?: Event.empty(),
                            createdAt = dto.createdAt
                        )
                    }*/
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

    suspend fun createEnrollment(event: Event, user: User): Enrollment?  {
        val enrollment = Enrollment(
            user = user,
            event = event
        )

        val bodyJson = gson.toJson(enrollment)
        println("📦 JSON enviado: $bodyJson")
        return try {
            val (_,_, result) = "${SERVER_ROOT}/api/enrollments"
                .httpPost()
                .header("Content-Type" to "application/json")
                .body(bodyJson)
                .awaitStringResponseResult()

            result.fold(
                success = { responseBody ->
                    println("Enrollment created successfuly!")
                    gson.fromJson(responseBody, Enrollment::class.java)

                    /*Enrollment(
                        id = enrollmentDto.id,
                        user = UserService.GetUserById(enrollmentDto.userId) ?: User.empty(),
                        event = EventService.getEventById(enrollmentDto.eventId) ?: Event.empty(),
                        createdAt = enrollmentDto.createdAt
                    )*/
                },
                failure = { error ->
                    println("Error creating new enrollment")
                    println("Body: ${error.response.body().asString("application/json")}")
                    null
                }
            )
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
