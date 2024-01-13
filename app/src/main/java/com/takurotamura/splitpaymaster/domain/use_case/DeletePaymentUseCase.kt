package com.takurotamura.splitpaymaster.domain.use_case

import com.takurotamura.splitpaymaster.data.entity.Payment
import com.takurotamura.splitpaymaster.domain.repository.PaymentRepository
import javax.inject.Inject

class DeletePaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository,
) {
    suspend fun deletePayment(payment: Payment) = paymentRepository.deletePayment(payment)
}