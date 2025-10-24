package com.app.matchup.samples

import android.os.Build
import androidx.annotation.RequiresApi
import com.app.matchup.models.Address
import com.app.matchup.models.Event
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import java.util.Date
import java.util.UUID
import com.app.matchup.R

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
                city = "Seixal",
                latitude = 38.822262,
                longitude = -9.139347
            ),
            cost = 3.0,
            duration = 60,
            gender = "M",
            sport = Sport(
                id = UUID.randomUUID(),
                name = "Football",
                icon = R.drawable.football_icon
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
                    city = "Seixal",
                    latitude = 38.621759,
                    longitude = -9.105657
                ),
                cost = 3.0,
                duration = 60,
                gender = "M",
                sport = Sport(
                    id = UUID.randomUUID(),
                    name = "Football",
                    icon = R.drawable.football_icon
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
                    street = "Rua de Moscavide n20",
                    zipCode = "3415-101",
                    city = "Lisboa",
                    latitude = 40.722252,
                    longitude = -8.139337
                ),
                cost = 2.0,
                duration = 90,
                maxMembers = 4,
                gender = "F",
                sport = Sport(
                    id = UUID.randomUUID(),
                    name = "Padel",
                    icon = R.drawable.padel_icon
                ),
                admin = user
            ),
            Event(
                id = UUID.randomUUID(),
                name = "Corrida Matinal",
                date = Date(),
                address = Address(
                    id = UUID.randomUUID(),
                    street = "Rua do Seixal n20",
                    zipCode = "2815-101",
                    city = "Seixal",
                    latitude = 38.645562,
                    longitude = -9.095290
                ),
                cost = 0.0,
                duration = 30,
                maxMembers = 2,
                gender = "Mix",
                sport = Sport(
                    id = UUID.randomUUID(),
                    name = "Running",
                    icon = R.drawable.running_icon
                ),
                admin = user,
                notes = "Trazer casaco e luvas"
            ),
            Event(
                id = UUID.randomUUID(),
                name = "Test Event",
                date = Date(),
                address = Address(
                    id = UUID.randomUUID(),
                    street = "Rua de Lisboa n150",
                    zipCode = "1115-101",
                    city = "Lisboa",
                    latitude = 38.722252,
                    longitude = -9.139337
                ),
                cost = 5.5,
                duration = 90,
                gender = "M",
                sport = Sport(
                    id = UUID.randomUUID(),
                    name = "Futsal",
                    icon = R.drawable.futsalball_icon
                ),
                admin = user
            ),
            createSampleEvent(),
            createSampleEvent()
        )
    }
}