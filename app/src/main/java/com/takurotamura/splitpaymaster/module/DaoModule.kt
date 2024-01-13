package com.takurotamura.splitpaymaster.module

import android.content.Context
import androidx.room.Room
import com.takurotamura.splitpaymaster.data.AppDatabase
import com.takurotamura.splitpaymaster.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    @Singleton
    fun appDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "split_pay_master_database"
    ).build()

    @Provides
    @Singleton
    fun eventDao(appDatabase: AppDatabase): EventDao = appDatabase.eventDao()

    @Provides
    @Singleton
    fun memberDao(appDatabase: AppDatabase): MemberDao = appDatabase.memberDao()

    @Provides
    @Singleton
    fun payeeDao(appDatabase: AppDatabase): PayeeDao = appDatabase.payeeDao()

    @Provides
    @Singleton
    fun payerDao(appDatabase: AppDatabase): PayerDao = appDatabase.payerDao()

    @Provides
    @Singleton
    fun paymentDao(appDatabase: AppDatabase): PaymentDao = appDatabase.paymentDao()
}