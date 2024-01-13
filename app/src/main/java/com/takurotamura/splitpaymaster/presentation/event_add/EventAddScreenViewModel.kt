package com.takurotamura.splitpaymaster.presentation.event_add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takurotamura.splitpaymaster.domain.use_case.AddEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class EventAddScreenViewModel @Inject constructor(
    private val addEventUseCase: AddEventUseCase,
) : ViewModel() {
    var eventName by mutableStateOf("")
    var eventDate by mutableStateOf(Instant.now().toEpochMilli())
    var memberName by mutableStateOf("")
    var memberList by mutableStateOf(emptyList<String>())
    var showValidationErrorDialog by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun createEvent() {
        viewModelScope.launch {
            addEventUseCase.createEvent(
                eventName = eventName,
                eventDate = Instant.ofEpochMilli(eventDate).atZone(ZoneId.systemDefault()).toLocalDate(),
                memberList = memberList
            )
        }
    }

    fun validateForm() {
        if (eventName.isEmpty()) {
            throw IllegalArgumentException("イベント名を入力してください")
        }

        if (memberList.isEmpty()) {
            throw IllegalArgumentException("メンバーを登録してください")
        }
    }
}