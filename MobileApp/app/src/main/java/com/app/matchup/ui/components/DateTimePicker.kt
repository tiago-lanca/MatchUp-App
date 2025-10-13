package com.app.matchup.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(
    onDateChanged: (Date) -> Unit
) {
    val openDatePicker = remember { mutableStateOf(false) }
    val openTimePicker = remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    val timePickerState = rememberTimePickerState()

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }

    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }
    val timeFormat = remember { DateTimeFormatter.ofPattern("HH:mm'h'") }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = selectedDate?.format(dateFormatter) ?: "",
                label = { Text("Date") },
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = selectedTime?.format(timeFormat) ?: "",
                label = { Text("Hour") },
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )


            Button(
                onClick = { openDatePicker.value = true },
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(35.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = "Pick Date and Time"
                )
            }
        }


        // Date Picker
        if (openDatePicker.value) {
            DatePickerDialog(
                onDismissRequest = { openDatePicker.value = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                selectedDate = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                openDatePicker.value = false
                                openTimePicker.value = true
                            } ?: run {
                                openDatePicker.value = false
                            }
                        }
                    ) { Text("OK") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // Time Picker
        if (openTimePicker.value) {
            TimePickerDialog(
                title = { Text("Time Picker")},
                onDismissRequest = { openTimePicker.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        selectedTime = LocalTime.of(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                        // Close time picker
                        openTimePicker.value = false

                        // Calls the callback of the event viewmodel to update the object's data
                        if(selectedDate != null && selectedTime != null){
                            val combinedDate = LocalDateTime.of(selectedDate, selectedTime)
                            val date = Date.from(
                                combinedDate.atZone(ZoneId.systemDefault()).toInstant()
                            )
                            onDateChanged(date)
                        }

                    }) {
                        Text("OK")
                    }
                }
            ) {
                TimePicker(state = timePickerState)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DateTimePickerPreview(){
    DateTimePicker(onDateChanged = { })
}