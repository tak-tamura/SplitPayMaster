package com.takurotamura.splitpaymaster.data.dao

import androidx.room.*
import com.takurotamura.splitpaymaster.data.entity.Event
import com.takurotamura.splitpaymaster.data.entity.EventWithMembers
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert
    suspend fun insert(event: Event): Long

    @Delete
    suspend fun delete(event: Event)

    @Update
    suspend fun update(event: Event)

    @Query("SELECT * FROM Event")
    fun selectAll(): Flow<List<Event>>

    @Query("SELECT * FROM Event WHERE eventName LIKE :eventName || '%'")
    fun selectByEventName(eventName: String): Flow<List<Event>>

    @Transaction
    @Query("SELECT * FROM Event WHERE eventId = :id")
    fun selectWithMembersById(id: Int): Flow<EventWithMembers>
}