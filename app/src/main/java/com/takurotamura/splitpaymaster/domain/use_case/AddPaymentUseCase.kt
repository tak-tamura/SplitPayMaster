package com.takurotamura.splitpaymaster.domain.use_case

import com.takurotamura.splitpaymaster.data.entity.Member
import com.takurotamura.splitpaymaster.data.entity.Payee
import com.takurotamura.splitpaymaster.data.entity.Payer
import com.takurotamura.splitpaymaster.data.entity.Payment
import com.takurotamura.splitpaymaster.domain.repository.PayeeRepository
import com.takurotamura.splitpaymaster.domain.repository.PayerRepository
import com.takurotamura.splitpaymaster.domain.repository.PaymentRepository
import java.time.LocalDate
import javax.inject.Inject

class AddPaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val payerRepository: PayerRepository,
    private val payeeRepository: PayeeRepository,
) {
    suspend fun createPayment(
        eventId: Int,
        label: String,
        amount: Int,
        paymentDate: LocalDate,
        payerList: List<Member>,
        payeeList: List<Member>,
    ) {
        val payment = Payment(
            label = label,
            eventId = eventId,
            amount = amount,
            paymentDate = paymentDate
        )
        val paymentId = paymentRepository.insertPayment(payment)

        payerList.forEach {
            payerRepository.insertPayer(Payer(paymentId = paymentId.toInt(), memberId = it.memberId))
        }

        payeeList.forEach {
            payeeRepository.insertPayee(Payee(paymentId = paymentId.toInt(), memberId = it.memberId))
        }
    }
}