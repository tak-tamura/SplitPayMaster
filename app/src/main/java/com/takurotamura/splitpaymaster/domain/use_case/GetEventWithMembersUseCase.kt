package com.takurotamura.splitpaymaster.domain.use_case

import com.takurotamura.splitpaymaster.data.entity.EventWithMembers
import com.takurotamura.splitpaymaster.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventWithMembersUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    fun getEventWithMembers(eventId: Int): Flow<EventWithMembers> =
        eventRepository.getEventsWithMembersById(eventId)
}