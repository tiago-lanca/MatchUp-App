package com.app.matchup.services

import com.app.matchup.models.Event
import com.app.matchup.models.User
import com.app.matchup.services.EnrollmentService.gson
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.GsonBuilder
import java.util.UUID

object EventService {
    suspend fun getEvents(): List<Event> {
        val (_,_, result) = "${SERVER_ROOT}/api/events"
            .httpGet()
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()

                gson.fromJson(responseBody, Array<Event>::class.java).toList()
            },
            failure = {
                emptyList<Event>()
            }
        )
    }

    suspend fun getEventById(id: UUID): Event?{
        val (_,_, result) = "${SERVER_ROOT}/api/events/$id"
            .httpGet()
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()

                gson.fromJson(responseBody, Event::class.java)
            },
            failure = {
                null
            }
        )
    }

    suspend fun getEventsByEnrolledUserId(userId: UUID): List<Event>{
        return try{
            val (_,_, result) = "${SERVER_ROOT}/api/events/user/${userId}"
                .httpGet()
                .awaitStringResponseResult()

            result.fold(
                success = { responseBody ->
                    gson.fromJson(responseBody, Array<Event>::class.java).toList()
                },
                failure = {
                    emptyList()
                }
            )
        }
        catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }
    suspend fun getEventAdmin(eventId: UUID): User?{
        val (_,_, result) = "${SERVER_ROOT}/api/events/${eventId}/admin"
            .httpGet()
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()

                val admin = gson.fromJson(responseBody, User::class.java)
                println(admin.name)
                admin
            },
            failure = { error ->
                println("Error getting event admin: ${error.message}")
                null
            }
        )
    }
    suspend fun createNewEvent(event: Event): Event?{
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        val bodyJson = gson.toJson(event)

        val (_,_, result) = "${SERVER_ROOT}/api/events"
            .httpPost()
            .header("Content-Type" to "application/json")
            .body(bodyJson)
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                println("Event created successfully!")
                gson.fromJson(responseBody, Event::class.java)

          },
            failure = { error ->
                println("Error creating new event: ${error.message}")
                null
            }
        )
    }
    suspend fun deleteEvent(eventId: UUID): Boolean {
        return try {
            val (_, _, result) = "${SERVER_ROOT}/api/events/${eventId}"
                .httpDelete()
                .awaitStringResponseResult()

            result.fold(
                success = { true },
                failure = {
                    println("Error deleting event: ${it.message}")
                    false
                }
            )
        }
        catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}