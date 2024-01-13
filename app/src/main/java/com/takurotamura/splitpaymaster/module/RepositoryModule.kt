package com.takurotamura.splitpaymaster.module

import com.takurotamura.splitpaymaster.data.dao.*
import com.takurotamura.splitpaymaster.data.repository.*
import com.takurotamura.splitpaymaster.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun eventRepository(eventDao: EventDao): EventRepository = EventRepositoryImpl(eventDao)

    @Provides
    @Singleton
    fun memberRepository(memberDao: MemberDao): MemberRepository = MemberRepositoryImpl(memberDao)

    @Provides
    @Singleton
    fun paymentRepository(paymentDao: PaymentDao): PaymentRepository = PaymentRepositoryImpl(paymentDao)

    @Provides
    @Singleton
    fun payerRepository(payerDao: PayerDao): PayerRepository = PayerRepositoryImpl(payerDao)

    @Provides
    @Singleton
    fun payeeRepository(payeeDao: PayeeDao): PayeeRepository = PayeeRepositoryImpl(payeeDao)
}