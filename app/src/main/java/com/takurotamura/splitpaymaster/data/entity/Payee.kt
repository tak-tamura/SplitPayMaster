package com.takurotamura.splitpaymaster.data.entity

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["paymentId", "memberId"],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Payment::class,
            parentColumns = ["paymentId"],
            childColumns = ["paymentId"],
            onDelete = androidx.room.ForeignKey.CASCADE,
        ),
        androidx.room.ForeignKey(
            entity = Member::class,
            parentColumns = ["memberId"],
            childColumns = ["memberId"],
            onDelete = androidx.room.ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["paymentId"], unique = false),
        Index(value = ["memberId"], unique = false)
    ]
)
data class Payee(
    val paymentId: Int,
    val memberId: Int,
)
