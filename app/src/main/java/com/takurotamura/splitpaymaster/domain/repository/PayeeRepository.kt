package com.takurotamura.splitpaymaster.domain.repository

import com.takurotamura.splitpaymaster.data.entity.Payee

interface PayeeRepository {
    suspend fun insertPayee(payee: Payee)

    suspend fun deletePayee(payee: Payee)
}