package com.app.matchup.services

import com.app.matchup.models.Enrollment
import com.app.matchup.models.Event
import com.app.matchup.models.User
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpDelete
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

            },
            failure = {
                emptyList<Enrollment>()
            }
        )
    }

    suspend fun getEnrollmentsByEventId(eventId: UUID): Int?{
        return try {
            val (_,_, result) = "${SERVER_ROOT}/api/enrollments/event/${eventId}/count-members"
                .httpGet()
                .awaitStringResponseResult()

            result.fold(
                success = { responseBody ->

                    gson.fromJson(responseBody, Int::class.java)

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

    suspend fun isUserEnrolled(eventId: UUID, userId: UUID): Boolean{
        return try {
            val (_,_, result) = "${SERVER_ROOT}/api/enrollments/event/${eventId}/user/${userId}/is-enrolled"
                .httpGet()
                .awaitStringResponseResult()

            result.fold(
                success = { responseBody ->

                    gson.fromJson(responseBody, Boolean::class.java)
                },
                failure = {
                    false
                }
            )
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteEnrollment(eventId: UUID, userId: UUID): Boolean{
        return try {
            val (_,_, result) = "${SERVER_ROOT}/api/enrollments/event/${eventId}/user/${userId}"
                .httpDelete()
                .awaitStringResponseResult()

            result.fold(
                success = { responseBody ->
                    println("Enrollment deleted successfuly!")
                    true
                },
                failure = { error ->
                    println("Error deleting enrollment")
                    println("Body: ${error.response.body().asString("application/json")}")
                    false
                }
            )
        }
        catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
