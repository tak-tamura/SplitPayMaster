package com.takurotamura.splitpaymaster.data.repository

import com.takurotamura.splitpaymaster.data.dao.PayerDao
import com.takurotamura.splitpaymaster.data.entity.Payer
import com.takurotamura.splitpaymaster.domain.repository.PayerRepository

class PayerRepositoryImpl(
    private val dao: PayerDao,
) : PayerRepository {
    override suspend fun insertPayer(payer: Payer) = dao.insert(payer)

    override suspend fun deletePayer(payer: Payer) = dao.delete(payer)
}