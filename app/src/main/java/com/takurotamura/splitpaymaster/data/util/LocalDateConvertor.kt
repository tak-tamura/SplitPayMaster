package com.takurotamura.splitpaymaster.data.util

import androidx.room.TypeConverter
import java.time.LocalDate

object LocalDateConvertor {
    @TypeConverter
    fun longToLocalDate(value: Long): LocalDate = LocalDate.ofEpochDay(value)

    @TypeConverter
    fun localDateToLong(value: LocalDate): Long = value.toEpochDay()
}