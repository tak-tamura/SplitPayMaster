package com.takurotamura.splitpaymaster.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.takurotamura.splitpaymaster.data.dao.*
import com.takurotamura.splitpaymaster.data.entity.*

@Database(
    entities = [
        Event::class,
        Member::class,
        Payee::class,
        Payer::class,
        Payment::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun memberDao(): MemberDao
    abstract fun payeeDao(): PayeeDao
    abstract fun payerDao(): PayerDao
    abstract fun paymentDao(): PaymentDao
}