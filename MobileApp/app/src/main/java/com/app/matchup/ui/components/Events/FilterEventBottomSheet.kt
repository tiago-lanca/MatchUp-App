package com.app.matchup.ui.components.Events

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.matchup.R
import com.app.matchup.models.Sport
import com.app.matchup.services.SportService
import com.app.matchup.ui.components.DatePickerDial
import com.app.matchup.ui.components.DateRangePickerDialog
import com.app.matchup.ui.components.DropdownMenuGeneric
import com.app.matchup.ui.theme.MatchUpTheme
import com.app.matchup.utilities.Tools
import com.app.matchup.viewmodels.EventFiltersViewModel
import com.app.matchup.viewmodels.EventsViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterEventBottomSheet(
    filtersVM: EventFiltersViewModel = viewModel(),
    context: Context,
    onDismiss: () -> Unit,
    eventVM: EventsViewModel = viewModel()
){
    val labelFontSize = 17.sp
    val filters by filtersVM.filters.collectAsState()
    var sports by remember { mutableStateOf<List<Sport>>(emptyList()) }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showDatePicker by remember { mutableStateOf(false) }
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val dateRangeText =
        if(filters.startDate != null && filters.startDate == filters.endDate) {
            "${formatter.format(filters.startDate)}"
        }
        else if(filters.startDate != null && filters.endDate != null){
            "${formatter.format(filters.startDate)} - ${formatter.format(filters.endDate)}"
        }
        else ""


    LaunchedEffect(Unit) {
        sports = SportService.getSports()
        sports += Sport(name = "Any")
        sheetState.expand()
    }

    if (showDatePicker) {
        DateRangePickerDialog(
            onDismiss = { showDatePicker = false },
            onRangeSelected = { startSelected, endSelected ->
                startSelected?.let { filtersVM.updateStartDate(Date.from(it.atStartOfDay(ZoneId.systemDefault()).toInstant())) }
                endSelected?.let { filtersVM.updateEndDate(Date.from(it.atStartOfDay(ZoneId.systemDefault()).toInstant())) }
                showDatePicker = false
            }
        )
    }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        // Top bar title "Filter" and X close button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            // Filter title
            Text(
                text = "Filter",
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )

            // X close button
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close_icon_desc),
                tint = Color.Black,
                modifier = Modifier
                    .padding(end = 15.dp)
                    .align(Alignment.CenterEnd)
                    .size(27.dp)
                    .clickable {
                        scope.launch {
                            sheetState.hide()
                            onDismiss()
                        }
                    }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Gender
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Gender: ",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = labelFontSize,
                    modifier = Modifier
                        .weight(0.6f)
                )
                DropdownMenuGeneric(
                    items = listOf("M", "F", "Mix", "Any"),
                    selectedItem = filters.gender ?: "Any",
                    leadingIcon = { Tools.GetGenderIcon(filters.gender ?: "Any") },
                    onItemSelected = { filtersVM.updateGender(it) },
                    getName = { it },
                    composableIcon = { Tools.GetGenderIcon(it) },
                    modifier = Modifier.weight(1f),
                    roundedCornerShapeDp = 15.dp
                )
            }
            // Sport
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sport:",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = labelFontSize,
                    modifier = Modifier
                        .weight(0.6f)
                )
                DropdownMenuGeneric(
                    items = sports,
                    selectedItem = filters.sport ?: Sport(name = "Any"),
                    leadingIcon = {
                        val sportIcon = filters.sport?.icon
                        if (sportIcon != null && sportIcon != 0) {
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
                    onItemSelected = { filtersVM.updateSport(it) },
                    getName = { it.name },
                    intIcon = { it.icon },
                    modifier = Modifier.weight(1f),
                    roundedCornerShapeDp = 15.dp
                )
            }

            // City
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "City: ",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = labelFontSize,
                    modifier = Modifier
                        .weight(0.6f)
                )
                TextField(
                    value = filters.city ?: "",
                    onValueChange = { filtersVM.updateCity(it) },
                    placeholder = { Text("Enter city to filter...") },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = stringResource(R.string.location_icon_desc),
                            tint = Color.Gray
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            // Date
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date: ",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = labelFontSize,
                    modifier = Modifier
                        .weight(0.6f)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePicker = true }
                ) {
                    TextField(
                        value = dateRangeText,
                        onValueChange = { },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = stringResource(R.string.calendar_icon_desc),
                                tint = Color.Gray
                            )
                        },
                        placeholder = { Text("Choose date...") },
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = Color.White,
                            disabledIndicatorColor = Color.Transparent,
                            disabledTextColor = Color.Black,
                            disabledLabelColor = Color.Black,
                            disabledPlaceholderColor = Color.Gray
                        ),
                    )
                }
            }


            // Only My Events Filter
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Show only my created events:",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = labelFontSize,
                    modifier = Modifier
                        .weight(0.7f)
                )
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Switch(
                        checked = filters.onlyMyEvents,
                        onCheckedChange = {
                            filtersVM.updateOnlyMyEvents(it)
                        },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            // Footer Cancel / OK buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Clear button
                Button(
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White
                    ),

                    onClick = { filtersVM.reset() }
                ) {
                    Text("Clear all",
                        fontSize = labelFontSize,
                        color = Color.Black,
                    )
                }

                // Show button
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = {
                        // apply filters()
                        scope.launch {
                            sheetState.hide()
                            onDismiss()
                        }
                    }
                ) {
                    Text(
                        text = "Show",
                        fontSize = labelFontSize,
                    )
                }
            }
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun FilterEventBottomSheetPreview(){
    MatchUpTheme (darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101010))
        ) {
            FilterEventBottomSheet(
                context = LocalContext.current,
                onDismiss = {}
            )
        }
    }
}