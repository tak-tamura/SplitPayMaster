package com.takurotamura.splitpaymaster.presentation.event_add.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.takurotamura.splitpaymaster.presentation.event_add.EventAddScreenViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDatePicker(viewModel: EventAddScreenViewModel = hiltViewModel()) {
    var showPicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(viewModel.eventDate)

    val formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd").withZone(ZoneId.systemDefault())
    var textDateState by remember {
        mutableStateOf(formatter.format(Instant.ofEpochMilli(viewModel.eventDate)))
    }

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.setSelection(
                            datePickerState.selectedDateMillis
                        )
                        viewModel.eventDate = datePickerState.selectedDateMillis!!
                        textDateState = formatter.format(Instant.ofEpochMilli(datePickerState.selectedDateMillis!!))
                        showPicker = false
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showPicker = false },
                ) {
                    Text(text = "キャンセル")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Text(text = "日付を選択")
    Box {
        TextField(
            value = textDateState,
            onValueChange = {},
            enabled = false,
            modifier = Modifier.clickable { showPicker = true }
        )
    }
}