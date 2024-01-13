package com.takurotamura.splitpaymaster.data.entity

import androidx.room.*
import com.takurotamura.splitpaymaster.data.util.LocalDateConvertor
import java.time.LocalDate

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["eventId"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["eventId"], unique = false)
    ]
)
@TypeConverters(LocalDateConvertor::class)
data class Payment(
    @PrimaryKey(autoGenerate = true) val paymentId: Int = 0,
    val label: String,
    val eventId: Int,
    val amount: Int,
    val paymentDate: LocalDate,
)
