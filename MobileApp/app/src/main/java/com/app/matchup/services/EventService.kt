package com.app.matchup.services

import com.app.matchup.enums.Status
import com.app.matchup.dtos.EventDTO
import com.app.matchup.models.Event
import com.app.matchup.models.User
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
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

                /*eventDtoList.map { dto ->
                    Event(
                        id = dto.id,
                        name = dto.name,
                        date = dto.date,
                        address = dto.address,
                        cost = dto.cost,
                        duration = dto.duration,
                        gender = dto.gender,
                        sport = dto.sport,
                        maxMembers = dto.maxMembers,
                        admin = UserService.GetUserById(dto.adminId),
                        notes = dto.notes,
                        status = dto.status ?: Status.CLOSED,
                        enrollments = EnrollmentService.getEnrollmentsByEventId(dto.id) ?: emptyList()
                    )
                }*/
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

                /*Event(
                    id = eventDto.id,
                    name = eventDto.name,
                    date = eventDto.date,
                    address = eventDto.address,
                    cost = eventDto.cost,
                    duration = eventDto.duration,
                    gender = eventDto.gender,
                    sport = eventDto.sport,
                    maxMembers = eventDto.maxMembers,
                    admin = UserService.GetUserById(eventDto.adminId),
                    notes = eventDto.notes,
                    status = eventDto.status ?: Status.CLOSED,
                    enrollments = EnrollmentService.getEnrollmentsByEventId(eventDto.id) ?: emptyList()
                )*/
            },
            failure = {
                null
            }
        )
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
                /*Event(
                    id = eventDto.id,
                    name = eventDto.name,
                    date = eventDto.date,
                    address = eventDto.address,
                    cost = eventDto.cost,
                    duration = eventDto.duration,
                    gender = eventDto.gender,
                    sport = eventDto.sport,
                    maxMembers = eventDto.maxMembers,
                    admin = UserService.GetUserById(eventDto.adminId),
                    notes = eventDto.notes,
                    status = eventDto.status ?: Status.CLOSED,
                    enrollments = EnrollmentService.getEnrollmentsByEventId(eventDto.id) ?: emptyList()
                )*/
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
                println("Event created successfuly!")
                gson.fromJson(responseBody, Event::class.java)

                /*Event(
                    id = eventDto.id,
                    name = eventDto.name,
                    date = eventDto.date,
                    address = eventDto.address,
                    cost = eventDto.cost,
                    duration = eventDto.duration,
                    gender = eventDto.gender,
                    sport = eventDto.sport,
                    maxMembers = eventDto.maxMembers,
                    admin = UserService.GetUserById(eventDto.adminId),
                    notes = eventDto.notes
                )*/
          },
            failure = { error ->
                println("Error creating new event: ${error.message}")
                null
            }
        )
    }
}