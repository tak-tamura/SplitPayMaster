package com.takurotamura.splitpaymaster.data.repository

import com.takurotamura.splitpaymaster.data.dao.PaymentDao
import com.takurotamura.splitpaymaster.data.entity.Payment
import com.takurotamura.splitpaymaster.data.entity.PaymentWithMembers
import com.takurotamura.splitpaymaster.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentRepositoryImpl (
    private val dao: PaymentDao,
) : PaymentRepository {
    override suspend fun insertPayment(payment: Payment): Long = dao.insert(payment)

    override suspend fun deletePayment(payment: Payment) = dao.delete(payment)

    override suspend fun updatePayment(payment: Payment) = dao.update(payment)

    override fun selectWithMembersByEventId(eventId: Int): Flow<List<PaymentWithMembers>>
        = dao.selectWithMembersByEventId(eventId)
}