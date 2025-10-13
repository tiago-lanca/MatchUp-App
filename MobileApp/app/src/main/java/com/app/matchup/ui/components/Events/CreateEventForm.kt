package com.app.matchup.ui.components.Events

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.matchup.R
import com.app.matchup.models.Event
import com.app.matchup.models.Sport
import com.app.matchup.ui.components.AddressSection
import com.app.matchup.ui.components.DateTimePicker
import com.app.matchup.ui.components.DropdownMenuGeneric
import com.app.matchup.ui.theme.GENDER_FEMALE_COLOR
import com.app.matchup.ui.theme.GENDER_MALE_COLOR
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateEventForm(
    event: Event,
    costInput: String,
    onNameChanged: (String) -> Unit,
    onDateChanged: (Date) -> Unit,
    onCostChanged: (String) -> Unit,
    onDurationChanged: (Int) -> Unit,
    onGenderChanged: (String) -> Unit,
    onSportChanged: (Sport) -> Unit,
    onMaxMembersChanged: (Int) -> Unit,
    onNotesChanged: (String) -> Unit,
    onCreateEvent: () -> Unit,
    modifier: Modifier = Modifier
){

    val sports = listOf<Sport>(
        Sport(
            name = "Football",
            icon = R.drawable.football_icon
        ),
        Sport(
            name = "Futsal",
            icon = R.drawable.football_icon
        ),
        Sport(
            name = "Corrida",
            icon = R.drawable.football_icon
        )
    )

    val genders = listOf<String>(
        "M",
        "F",
        "Mix"
    )

    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Event Name Field
        TextField(
            value = event.name,
            onValueChange = onNameChanged,
            label = { Text(text = "Event Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Address (Street, City, Zip Code) Field
        AddressSection()

        Spacer(modifier = Modifier.height(10.dp))

        Row (
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Sports Field
            DropdownMenuGeneric(
                label = "Sport",
                items = sports,
                selectedItem = event.sport,
                onItemSelected = { onSportChanged(it) },
                backgroundColor = Color(0xFFE7E0EC),
                leadingIcon = {
                    event.sport?.icon?.let { sportIcon ->
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(sportIcon),
                                contentDescription = "Flag",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                },
                getName = { it.name },
                modifier = Modifier.weight(2f)
            )

            // Max Members
            TextField(
                value = event.maxMembers.toString(),
                onValueChange = { onMaxMembersChanged(it.toInt()) },
                label = {
                    Text(
                        text = "Members"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f)
            )


        }

        DateTimePicker(onDateChanged)

        Row (
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            // Gender Field
            DropdownMenuGeneric(
                label = "Gender",
                items = genders,
                selectedItem = event.gender,
                onItemSelected = { onGenderChanged(it) },
                backgroundColor = Color(0xFFE7E0EC),
                leadingIcon = {
                    if(event.gender == "M") {
                        Icon(
                            imageVector = Icons.Filled.Male,
                            contentDescription = "Male Gender Icon",
                            tint = GENDER_MALE_COLOR
                        )
                    }
                    else
                        Icon(
                            imageVector = Icons.Filled.Female,
                            contentDescription = "Female Gender Icon",
                            tint = GENDER_FEMALE_COLOR
                        )
                },
                getName = { it },
                modifier = Modifier.weight(1f)
            )

            // Duration Field
            TextField(
                value = event.duration.toString(),
                onValueChange = {
                    onDurationChanged(it.toInt())
                },
                label = { Text(text = "Duration (min)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(0.7f)
            )

            // Cost Field
            TextField(
                value = costInput,
                onValueChange = { onCostChanged(it) },
                label = { Text(text = "€/p") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(0.4f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Notes Field
        TextField(
            value = event.notes ?: "",
            onValueChange = { onNotesChanged(it) },
            label = { Text(text = "Notes") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun CreateEventFormPreview() {
    val event = Event(
        name = "Football Match",
        date = Date(),
        cost = 10.0,
        duration = 90,
        sport = Sport(name="Football", icon = R.drawable.football_icon),
        gender = "M",
        notes = "Bring your own water bottle."
    )
    CreateEventForm(
        event = event,
        costInput = event.cost.toString(),
        onNameChanged = {},
        onDateChanged = {},
        onCostChanged = {},
        onDurationChanged = {},
        onGenderChanged = {},
        onSportChanged = {},
        onMaxMembersChanged = {},
        onNotesChanged = {},
        onCreateEvent = {}
    )
}

