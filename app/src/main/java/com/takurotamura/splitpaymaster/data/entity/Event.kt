package com.takurotamura.splitpaymaster.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.takurotamura.splitpaymaster.data.util.LocalDateConvertor
import java.time.LocalDate

@Entity
@TypeConverters(LocalDateConvertor::class)
data class Event(
    @PrimaryKey(autoGenerate = true) val eventId: Int = 0,
    val eventName: String,
    val eventDate: LocalDate,
    val memberCount: Int,
)