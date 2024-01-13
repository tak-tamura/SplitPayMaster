package com.takurotamura.splitpaymaster.data.dao

import androidx.room.*
import com.takurotamura.splitpaymaster.data.entity.Payment
import com.takurotamura.splitpaymaster.data.entity.PaymentWithMembers
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Insert
    suspend fun insert(payment: Payment): Long

    @Delete
    suspend fun delete(payment: Payment)

    @Update
    suspend fun update(payment: Payment)

    @Transaction
    @Query("SELECT * FROM Payment WHERE eventId = :eventId")
    fun selectWithMembersByEventId(eventId: Int): Flow<List<PaymentWithMembers>>
}