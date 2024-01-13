package com.takurotamura.splitpaymaster.data.repository

import com.takurotamura.splitpaymaster.data.dao.EventDao
import com.takurotamura.splitpaymaster.data.entity.Event
import com.takurotamura.splitpaymaster.data.entity.EventWithMembers
import com.takurotamura.splitpaymaster.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl (
    private val dao: EventDao
) : EventRepository {
    override suspend fun insertEvent(event: Event): Long = dao.insert(event)

    override suspend fun deleteEvent(event: Event) = dao.delete(event)

    override suspend fun updateEvent(event: Event) = dao.update(event)

    override fun getAllEvents(): Flow<List<Event>> = dao.selectAll()

    override fun getEventsByName(eventName: String): Flow<List<Event>>
        = dao.selectByEventName(eventName)

    override fun getEventsWithMembersById(id: Int): Flow<EventWithMembers>
        = dao.selectWithMembersById(id)
}