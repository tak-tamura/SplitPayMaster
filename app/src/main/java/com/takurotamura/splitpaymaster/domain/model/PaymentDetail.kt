package com.takurotamura.splitpaymaster.domain.model

data class PaymentDetail(
    val amount: Int,
    val from: String,
    val to: String,
)
