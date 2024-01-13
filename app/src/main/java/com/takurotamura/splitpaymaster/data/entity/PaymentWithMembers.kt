package com.takurotamura.splitpaymaster.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class PaymentWithMembers(
    @Embedded val payment: Payment,
    @Relation(
        parentColumn = "paymentId",
        entityColumn = "memberId",
        associateBy = Junction(Payer::class)
    )
    val payers: List<Member>,
    @Relation(
        parentColumn = "paymentId",
        entityColumn = "memberId",
        associateBy = Junction(Payee::class)
    )
    val payees: List<Member>
)
