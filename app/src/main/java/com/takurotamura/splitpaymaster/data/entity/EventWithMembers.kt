package com.takurotamura.splitpaymaster.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class EventWithMembers(
    @Embedded val event: Event,
    @Relation(
        parentColumn = "eventId",
        entityColumn = "eventId",
    )
    val members: List<Member>
)
