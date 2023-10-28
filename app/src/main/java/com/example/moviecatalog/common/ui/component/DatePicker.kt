package com.example.moviecatalog.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.common.ui.theme.label15MTextStyle
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateField(
    formText: String,
    pickedDate: LocalDate?,
    isError: Boolean,
    onPickedDateChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (!isError) Color.Transparent else colorResource(R.color.dark_accent)
    val borderColor = if (!isError) colorResource(R.color.border_color) else colorResource(R.color.accent)
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    Text(
        text = formText,
        style = label15MTextStyle
    )
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .background(containerColor)
            .padding(start = 12.dp),
        horizontalArrangement = if (pickedDate != null) Arrangement.SpaceBetween else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (pickedDate != null) {
            Text(text = dateFormatter.format(pickedDate))
        }

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
            initialSelectedDateMillis = pickedDate?.toEpochDay() ?: LocalDate.now().toEpochDay(),
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

@Preview
@Composable
fun PreviewCustomDateField(){
    CustomDateField(
        formText = "",
        pickedDate = LocalDate.now(),
        isError = true,
        onPickedDateChanged = {}
    )
}