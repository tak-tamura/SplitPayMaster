package com.takurotamura.splitpaymaster.domain.use_case

import com.takurotamura.splitpaymaster.data.entity.Member
import com.takurotamura.splitpaymaster.data.entity.PaymentWithMembers
import com.takurotamura.splitpaymaster.domain.model.PaymentDetail
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class CalculatePaymentsUseCase @Inject constructor() {
    fun calculatePayments(
        members: List<Member>,
        payments: List<PaymentWithMembers>
    ): List<PaymentDetail> {
        val paymentMapA = mutableMapOf<Int, MutableMap<Int, BigDecimal>>()
        val memberIdToName = members.associateBy({it.memberId}, {it.name})
        val memberIdList = members.map { it.memberId }

        for (memberId in memberIdList) {
            val paymentMapB = memberIdList.filter { it != memberId }
                                          .associateWith { BigDecimal.ZERO }
                                          .toMutableMap()
            paymentMapA[memberId] = paymentMapB
        }

        for (paymentWithMembers in payments) {
            val payers = paymentWithMembers.payers
            val payees = paymentWithMembers.payees
            val amountPerOnePerson =
                BigDecimal(paymentWithMembers.payment.amount) / BigDecimal(payers.size + payees.size)

            for (payee in payees) {
                paymentMapA[payee.memberId]?.let { mapForPayee ->
                    for (payer in payers) {
                        mapForPayee[payer.memberId]?.let {
                            mapForPayee[payer.memberId] = it + amountPerOnePerson
                        }
                    }
                }
            }

            for (payer in payers) {
                paymentMapA[payer.memberId]?.let { mapForPayer ->
                    for (payee in payees) {
                        mapForPayer[payee.memberId]?.let {
                            mapForPayer[payee.memberId] = it - amountPerOnePerson
                        }
                    }
                }
            }
        }

        val paymentDetails = mutableListOf<PaymentDetail>()

        for (entry1 in paymentMapA.entries.iterator()) {
            val payeeId = entry1.key

            for (entry2 in entry1.value) {
                val payerId = entry2.key
                val amount = entry2.value

                if (amount <= BigDecimal.ZERO) {
                    continue
                }

                paymentDetails.add(
                    PaymentDetail(
                        amount = amount.setScale(0, RoundingMode.HALF_UP).intValueExact(),
                        from = memberIdToName[payeeId] ?: "",
                        to = memberIdToName[payerId] ?: ""
                    )
                )
            }
        }

        return paymentDetails
    }
}