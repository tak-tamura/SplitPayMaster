package com.takurotamura.splitpaymaster.domain.model

import com.takurotamura.splitpaymaster.data.entity.Member
import com.takurotamura.splitpaymaster.data.entity.PaymentWithMembers

data class EventDetail(
    val eventId: Int,
    val eventName: String,
    val memberList: List<Member>,
    val paymentList: List<PaymentWithMembers>,
)
