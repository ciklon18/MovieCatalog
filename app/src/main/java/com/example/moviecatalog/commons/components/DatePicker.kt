package com.example.moviecatalog.commons.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateField(
    pickedDate: LocalDate,
    onPickedDateChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(R.color.border_color),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(start = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = dateFormatter.format(pickedDate))

        IconButton(onClick = { isDatePickerVisible = true }) {
            Icon(
                painter = painterResource(R.drawable.date),
                contentDescription = stringResource(R.string.date)
            )
        }
    }

    val datePickerState = remember {
        DatePickerState(
            yearRange = (1970..2023),
            initialSelectedDateMillis = pickedDate.toEpochDay(),
            initialDisplayMode = DisplayMode.Picker,
            initialDisplayedMonthMillis = null
        )
    }
    if (isDatePickerVisible) {
        DatePickerAlertDialog(
            datePickerState = datePickerState,
            onDismissRequest = { isDatePickerVisible = false },
            confirmButton = {
                Button(onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    if (selectedDateMillis != null) {
                        onPickedDateChanged(
                            Instant.ofEpochMilli(selectedDateMillis).atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        )
                    }
                    isDatePickerVisible = false
                }) {
                    Text(text = stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                Button(onClick = { isDatePickerVisible = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerAlertDialog(
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit,
    dismissButton: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit
) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        dismissButton = dismissButton,
        confirmButton = confirmButton
    ) {
        DatePicker(state = datePickerState)
    }
}
