package com.takurotamura.splitpaymaster.domain.use_case

import com.takurotamura.splitpaymaster.data.entity.PaymentWithMembers
import com.takurotamura.splitpaymaster.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository,
) {
    fun getPaymentWithMembersByEventId(eventId: Int): Flow<List<PaymentWithMembers>> =
        paymentRepository.selectWithMembersByEventId(eventId)
}