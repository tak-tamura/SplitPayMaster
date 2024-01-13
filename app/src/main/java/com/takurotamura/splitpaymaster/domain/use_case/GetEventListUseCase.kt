package com.takurotamura.splitpaymaster.domain.use_case

import com.takurotamura.splitpaymaster.data.entity.Event
import com.takurotamura.splitpaymaster.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventListUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    fun getAllEventList(): Flow<List<Event>> = eventRepository.getAllEvents()

    fun getEventListByName(eventName: String): Flow<List<Event>> =
        eventRepository.getEventsByName(eventName)
}