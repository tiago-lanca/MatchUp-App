package com.app.matchup.samples

import android.os.Build
import androidx.annotation.RequiresApi
import com.app.matchup.models.Address
import com.app.matchup.models.Event
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
object EventSamples {
    fun createSampleEvent(): Event {

        val user = User(
            id = UUID.randomUUID(),
            name = "Tiago Lança",
            email = "tiagotestest@email.com",
            passwordHash = "1234"
        )
        return Event(
            id = UUID.randomUUID(),
            name = "Test Event",
            date = Date(),
            address = Address(
                id = UUID.randomUUID(),
                street = "Rua dos Testes n10",
                zipCode = "1886-502",
                city = "Seixal"
            ),
            cost = 3.0,
            duration = 60,
            gender = "M",
            sport = Sport(
                id = UUID.randomUUID(),
                name = "Football"
            ),
            admin = user,
            notes = "This is a test event"
        )
    }

    fun createSampleEmptyListEvents(): List<Event> = emptyList()



    @RequiresApi(Build.VERSION_CODES.O)
    fun createSampleListEvents(): List<Event> {
        val user = User(
            id = UUID.randomUUID(),
            name = "Tiago Lança",
            email = "tiagotestest@email.com",
            passwordHash = "1234"
        )
        return listOf<Event>(
            Event(
                id = UUID.randomUUID(),
                name = "Test Event",
                date = Date(),
                address = Address(
                    id = UUID.randomUUID(),
                    street = "Rua dos Testes n10",
                    zipCode = "1886-502",
                    city = "Seixal"
                ),
                cost = 3.0,
                duration = 60,
                gender = "M",
                sport = Sport(
                    id = UUID.randomUUID(),
                    name = "Football"
                ),
                admin = user,
                notes = "This is a test event"
            ),
            Event(
                id = UUID.randomUUID(),
                name = "Test Event",
                date = Date(),
                address = Address(
                    id = UUID.randomUUID(),
                    street = "Rua de Lisboa n150",
                    zipCode = "1115-101",
                    city = "Lisboa"
                ),
                cost = 3.0,
                duration = 60,
                gender = "M",
                sport = Sport(
                    id = UUID.randomUUID(),
                    name = "Football"
                ),
                admin = user
            ),
            createSampleEvent(),
            createSampleEvent()
        )
    }
}