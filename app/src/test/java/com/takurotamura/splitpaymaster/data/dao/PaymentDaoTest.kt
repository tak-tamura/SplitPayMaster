package com.takurotamura.splitpaymaster.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.takurotamura.splitpaymaster.data.AppDatabase
import com.takurotamura.splitpaymaster.data.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDate

@RunWith(RobolectricTestRunner::class)
class PaymentDaoTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var db: AppDatabase
    private lateinit var paymentDao: PaymentDao
    private lateinit var eventDao: EventDao
    private lateinit var memberDao: MemberDao
    private lateinit var payeeDao: PayeeDao
    private lateinit var payerDao: PayerDao


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .setQueryExecutor(testDispatcher.asExecutor())
            .setTransactionExecutor(testDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()
        eventDao = db.eventDao()
        memberDao = db.memberDao()
        paymentDao = db.paymentDao()
        payeeDao = db.payeeDao()
        payerDao = db.payerDao()
    }

    @After
    fun after() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_selectWithMembersByEventId() = runTest {
        val result = mutableListOf<List<PaymentWithMembers>>()
        val job = launch {
            paymentDao.selectWithMembersByEventId(eventId = 1).toList(result)
        }

        val event = Event(
            eventId = 1,
            eventName = "NewZealand Travel",
            eventDate = LocalDate.now(),
            memberCount = 2
        )
        eventDao.insert(event)

        val member1 = Member(memberId = 1, eventId = 1,name = "John Smith")
        val member2 = Member(memberId = 2, eventId = 1,name = "Ken Tanaka")
        val member3 = Member(memberId = 3, eventId = 1,name = "Chen Meng")
        memberDao.insert(member1)
        memberDao.insert(member2)
        memberDao.insert(member3)

        val payment = Payment(
            paymentId = 1,
            eventId = 1,
            amount = 2000,
            paymentDate = LocalDate.now()
        )
        paymentDao.insert(payment)
        payerDao.insert(Payer(paymentId = 1, memberId = 1))
        payeeDao.insert(Payee(paymentId = 1, memberId = 2))
        payeeDao.insert(Payee(paymentId = 1, memberId = 3))
        advanceUntilIdle()

        MatcherAssert.assertThat(
            result[5],
            CoreMatchers.equalTo(
                listOf(
                    PaymentWithMembers(
                        payment = payment,
                        payers = listOf(member1),
                        payees = listOf(member2, member3)
                    )
                )
            )
        )

        job.cancel()
    }
}