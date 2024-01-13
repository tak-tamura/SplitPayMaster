package com.takurotamura.splitpaymaster.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["eventId"])],
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["eventId"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class Member(
    @PrimaryKey(autoGenerate = true) val memberId: Int = 0,
    val eventId: Int,
    val name: String,
)
