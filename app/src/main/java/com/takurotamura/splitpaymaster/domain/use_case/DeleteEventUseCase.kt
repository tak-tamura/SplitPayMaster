package com.takurotamura.splitpaymaster.domain.use_case

import com.takurotamura.splitpaymaster.data.entity.Event
import com.takurotamura.splitpaymaster.domain.repository.EventRepository
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend fun deleteEvent(event: Event) {
        eventRepository.deleteEvent(event)
    }
}