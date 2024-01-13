package com.takurotamura.splitpaymaster.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.takurotamura.splitpaymaster.data.entity.Payee

@Dao
interface PayeeDao {
    @Insert
    suspend fun insert(payee: Payee)

    @Delete
    suspend fun delete(payee: Payee)
}