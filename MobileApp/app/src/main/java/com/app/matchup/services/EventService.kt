package com.app.matchup.services

import com.app.matchup.dtos.EventDTO
import com.app.matchup.models.Event
import com.app.matchup.utilities.AppConstants.SERVER_ROOT
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder

class EventService {
    private val serverRoot = "http://10.0.2.2:8081"

    fun GetEvents(
        callback: (eventList: List<Event>) -> Unit
    ) {

        var eventList = mutableListOf<Event>()
        "${SERVER_ROOT}/api/events".httpGet().response {
              request, response, result ->

            val responseBody = String(response.data)
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()
            val json = gson.fromJson(responseBody, Array<EventDTO>::class.java)

            val eventDtoList = json.toList()
            for(eventDto in eventDtoList){
                val event = Event(
                    id = eventDto.id,
                    name = eventDto.name,
                    date = eventDto.date,
                    address = eventDto.address,
                    cost = eventDto.cost,
                    duration = eventDto.duration,
                    gender = eventDto.gender,
                    sport = eventDto.sport,
                    maxMembers = eventDto.maxMembers,
                    admin = UserService().get
                )
            }
            public final val id: UUID = UUID.randomUUID(),
            public final var name: String = "",
            public final var date: Date = Date(),
            public final var address: Address? = null,
            public final var cost: Double = 0.0,
            public final var duration: Int = 0,
            public final var gender: String = "M",
            public final var sport: Sport? = null,
            public final var maxMembers: Int = 0,
            public final var admin: User? = null,
            public final var notes: String? = null,
            public final var status: Status = Status.OPEN,
            public final var enrollments: List<Enrollment> = emptyList<Enrollment>()

            callback(json.toList())
        }
    }
}