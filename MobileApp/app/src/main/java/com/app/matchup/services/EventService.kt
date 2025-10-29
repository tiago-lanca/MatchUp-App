package com.app.matchup.services

import com.app.matchup.Status
import com.app.matchup.dtos.EventDTO
import com.app.matchup.models.Event
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder
import java.util.UUID

class EventService {
    suspend fun getEvents(): List<Event> {
        val (_,_, result) = "${SERVER_ROOT}/api/events"
            .httpGet()
            .awaitStringResponseResult()

        return result.fold(
            success = { responseBody ->
                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()

                val eventDtoList = gson.fromJson(responseBody, Array<EventDTO>::class.java).toList()

                eventDtoList.map { dto ->
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
                        admin = UserService().GetUserById(dto.adminId),
                        notes = dto.notes,
                        status = dto.status ?: Status.CLOSED,
                        enrollments = EnrollmentService().GetEnrollmentsByEventId(dto.id) ?: emptyList()
                    )
                }
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

                val eventDto = gson.fromJson(responseBody, EventDTO::class.java)

                Event(
                    id = eventDto.id,
                    name = eventDto.name,
                    date = eventDto.date,
                    address = eventDto.address,
                    cost = eventDto.cost,
                    duration = eventDto.duration,
                    gender = eventDto.gender,
                    sport = eventDto.sport,
                    maxMembers = eventDto.maxMembers,
                    admin = UserService().GetUserById(eventDto.adminId),
                    notes = eventDto.notes,
                    status = eventDto.status ?: Status.CLOSED,
                    enrollments = EnrollmentService().GetEnrollmentsByEventId(eventDto.id) ?: emptyList()
                )
            },
            failure = {
                null
            }
        )
    }
}