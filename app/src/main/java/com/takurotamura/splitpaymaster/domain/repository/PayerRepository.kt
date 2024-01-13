package com.takurotamura.splitpaymaster.domain.repository

import com.takurotamura.splitpaymaster.data.entity.Payer

interface PayerRepository {
    suspend fun insertPayer(payer: Payer)

    suspend fun deletePayer(payer: Payer)
}