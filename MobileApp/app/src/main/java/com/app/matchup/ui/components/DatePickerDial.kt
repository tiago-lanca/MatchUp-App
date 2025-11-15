package com.app.matchup.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.app.matchup.R
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerDial(
    onDismiss: () -> Unit,
    onDateSelected: (Date) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val today = LocalDate.now()
    val todayMillis = today
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    // Allow to select dates from today forward
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayMillis
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if(millis != null){
                        onDateSelected(Date(millis))
                    }
                }
            ){
                Text(stringResource(R.string.ok_button_label))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_button_label))
            }
        }
    ){
        DatePicker(state = datePickerState)
    }
}