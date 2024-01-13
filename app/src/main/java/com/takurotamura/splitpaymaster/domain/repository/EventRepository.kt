package com.takurotamura.splitpaymaster.domain.repository

import com.takurotamura.splitpaymaster.data.entity.Event
import com.takurotamura.splitpaymaster.data.entity.EventWithMembers
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun insertEvent(event: Event): Long

    suspend fun deleteEvent(event: Event)

    suspend fun updateEvent(event: Event)

    fun getAllEvents(): Flow<List<Event>>

    fun getEventsByName(eventName: String): Flow<List<Event>>

    fun getEventsWithMembersById(id: Int): Flow<EventWithMembers>
}