package com.takurotamura.splitpaymaster.presentation.event_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takurotamura.splitpaymaster.data.entity.Event
import com.takurotamura.splitpaymaster.domain.use_case.DeleteEventUseCase
import com.takurotamura.splitpaymaster.domain.use_case.GetEventListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListScreenViewModel @Inject constructor(
    private val getEventListUseCase: GetEventListUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
) : ViewModel() {
    private val _eventsLiveData = MutableLiveData<List<Event>>()
    val eventsLiveData: LiveData<List<Event>> = _eventsLiveData

    var showDeleteConfirmDialog by mutableStateOf(false)
    var query by mutableStateOf("")

    lateinit var eventToDelete: Event

    init {
        viewModelScope.launch {
            getEventListUseCase.getAllEventList().collect() {
                _eventsLiveData.value = it
            }
        }
    }

    fun searchEvents() {
        viewModelScope.launch {
            if (query.isNotBlank()) {
                getEventListUseCase.getEventListByName(query).collect() {
                    _eventsLiveData.value = it
                }
            } else {
                getEventListUseCase.getAllEventList().collect() {
                    _eventsLiveData.value = it
                }
            }
        }
    }

    fun deleteEvent() {
        viewModelScope.launch {
            deleteEventUseCase.deleteEvent(eventToDelete)
        }
    }
}