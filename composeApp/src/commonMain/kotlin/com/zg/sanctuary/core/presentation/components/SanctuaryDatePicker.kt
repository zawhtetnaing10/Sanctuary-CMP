package com.zg.sanctuary.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zg.sanctuary.core.MARGIN_LARGE
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.date_of_birth
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun SanctuaryDatePicker(
    dob: String,
    onDobChanged: (String) -> Unit,
) {

    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        // Date Picker dialog
        DatePickerDialog(
            colors = DatePickerDefaults.colors(containerColor = Color.White),
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                SanctuaryPrimaryButton(
                    title = "Ok",
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val dateInstant : Instant = Instant.fromEpochMilliseconds(millis)
                            val dateTime: LocalDateTime = dateInstant.toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
                            val formattedDate = "${dateTime.year}-${dateTime.monthNumber.toString().padStart(2, '0')}-${
                                dateTime.dayOfMonth.toString().padStart(2, '0')
                            }"
                            onDobChanged(formattedDate)
                        }
                        showDatePicker = false
                    }
                )
            },
            dismissButton = {
                SanctuaryPrimaryButton(
                    "Cancel",
                    onClick = {
                        showDatePicker = false
                    }
                )
            }
        ) {
            DatePicker(state = datePickerState, colors = DatePickerDefaults.colors(containerColor = Color.White))
        }
    }

    SanctuaryTextField(
        inputText = dob,
        onInputChanged = {
            // Do Nothing
        },
        hint = stringResource(Res.string.date_of_birth),
        isEnabled = false,
        modifier = Modifier.padding(top = MARGIN_LARGE)
            .clickable {
                showDatePicker = !showDatePicker
            }
    )
}