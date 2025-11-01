package com.app.matchup.ui.components.Events

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.matchup.R
import com.app.matchup.models.CreateEventValidation
import com.app.matchup.models.Event
import com.app.matchup.models.Sport
import com.app.matchup.services.SportService
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
    durationInput: String,
    maxMembersInput: String,
    validationState: CreateEventValidation,
    onNameChanged: (String) -> Unit,
    onDateChanged: (Date) -> Unit,
    onCostChanged: (String) -> Unit,
    onDurationChanged: (String) -> Unit,
    onGenderChanged: (String) -> Unit,
    onSportChanged: (Sport) -> Unit,
    onMaxMembersChanged: (String) -> Unit,
    onNotesChanged: (String) -> Unit,
    onCreateEvent: () -> Unit,
    modifier: Modifier = Modifier
){
    var sports by remember { mutableStateOf<List<Sport>>(emptyList()) }

    LaunchedEffect(Unit) {
        sports = SportService.getSports()
        event.sport = sports.first()
    }

    val genders = listOf<String>(
        "M",
        "F",
        "Mix"
    )

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Event Name Field
        TextField(
            value = event.name,
            onValueChange = onNameChanged,
            label = { Text(text = "Event Name") },
            isError = validationState.nameError != null,
            modifier = Modifier.fillMaxWidth()
        )

        // Address (Street, City, Zip Code) Field
        AddressSection(event.address)

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
                            modifier = Modifier.padding(start = 4.dp, end = 0.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(sportIcon),
                                contentDescription = "Sport icon",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                },
                isError = validationState.sportError != null,
                getName = { it.name },
                getIcon = { it.icon },
                modifier = Modifier.weight(2f)
            )

            // Max Members
            TextField(
                value = maxMembersInput,
                onValueChange = { onMaxMembersChanged(it) },
                label = {
                    Text(
                        text = "Members"
                    )
                },
                isError = validationState.maxMembersError != null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f)
            )
        }

        DateTimePicker(onDateChanged, validationState.dateError)

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
                isError = validationState.genderError != null,
                modifier = Modifier.weight(1f)
            )

            // Duration Field
            TextField(
                value = durationInput,
                onValueChange = { onDurationChanged(it) },
                label = { Text(
                    text = "Duration (min)",
                    fontSize = 14.sp
                    )
                },
                isError = validationState.durationError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .weight(0.95f)
            )

            // Cost Field
            TextField(
                value = costInput,
                onValueChange = { onCostChanged(it) },
                label = { Text(text = "€/p") },
                isError = validationState.costError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .weight(0.45f)
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
        durationInput = event.duration.toString(),
        maxMembersInput = event.maxMembers.toString(),
        validationState = CreateEventValidation(),
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

