package com.takurotamura.splitpaymaster.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.takurotamura.splitpaymaster.data.entity.Member
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Insert
    suspend fun insert(member: Member): Long

    @Query("SELECT * FROM Member WHERE eventId = :eventId")
    fun selectByEventId(eventId: Int): Flow<List<Member>>
}