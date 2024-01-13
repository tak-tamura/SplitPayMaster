package com.takurotamura.splitpaymaster.domain.repository

import com.takurotamura.splitpaymaster.data.entity.Payment
import com.takurotamura.splitpaymaster.data.entity.PaymentWithMembers
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun insertPayment(payment: Payment): Long

    suspend fun deletePayment(payment: Payment)

    suspend fun updatePayment(payment: Payment)

    fun selectWithMembersByEventId(eventId: Int): Flow<List<PaymentWithMembers>>
}