package com.takurotamura.splitpaymaster.data.repository

import com.takurotamura.splitpaymaster.data.dao.PayeeDao
import com.takurotamura.splitpaymaster.data.entity.Payee
import com.takurotamura.splitpaymaster.domain.repository.PayeeRepository

class PayeeRepositoryImpl(
    private val dao: PayeeDao,
) : PayeeRepository {
    override suspend fun insertPayee(payee: Payee) = dao.insert(payee)

    override suspend fun deletePayee(payee: Payee) = dao.insert(payee)
}