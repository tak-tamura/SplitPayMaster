package com.takurotamura.splitpaymaster.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.takurotamura.splitpaymaster.data.entity.Payer

@Dao
interface PayerDao {
    @Insert
    suspend fun insert(payer: Payer)

    @Delete
    suspend fun delete(payer: Payer)
}