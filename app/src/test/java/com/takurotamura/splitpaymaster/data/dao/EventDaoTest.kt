package com.takurotamura.splitpaymaster.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.takurotamura.splitpaymaster.data.AppDatabase
import com.takurotamura.splitpaymaster.data.entity.Event
import com.takurotamura.splitpaymaster.data.entity.EventWithMembers
import com.takurotamura.splitpaymaster.data.entity.Member
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
class EventDaoTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var db: AppDatabase
    private lateinit var eventDao: EventDao
    private lateinit var memberDao: MemberDao

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
    }

    @After
    fun after() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_selectAll() = runTest {
        val result = mutableListOf<List<Event>>()
        val job = launch {
            eventDao.selectAll().toList(result)
        }

        advanceUntilIdle()
        val insertEntity1 = Event(eventId = 1, eventName = "NewZealand Travel", eventDate = LocalDate.now(), memberCount = 2)
        eventDao.insert(insertEntity1)
        advanceUntilIdle()
        val insertEntity2 = Event(eventId = 2, eventName = "Kochi Travel", eventDate = LocalDate.now(), memberCount = 5)
        eventDao.insert(insertEntity2)
        advanceUntilIdle()

        MatcherAssert.assertThat(result[0], CoreMatchers.equalTo(emptyList()))
        MatcherAssert.assertThat(result[1], CoreMatchers.equalTo(listOf(insertEntity1)))
        MatcherAssert.assertThat(result[2], CoreMatchers.equalTo(listOf(insertEntity1, insertEntity2)))

        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_selectWithMembersById() = runTest {
        val result = mutableListOf<EventWithMembers>()
        val job = launch {
            eventDao.selectWithMembersById(id = 1).toList(result)
        }

        val event = Event(
            eventId = 1,
            eventName = "NewZealand Travel",
            eventDate = LocalDate.now(),
            memberCount = 2
        )
        eventDao.insert(event)

        val member1 = Member(memberId = 1, eventId = 1, name = "John Smith")
        val member2 = Member(memberId = 2, eventId = 1, name = "Ken Tanaka")
        memberDao.insert(member1)
        memberDao.insert(member2)
        advanceUntilIdle()

        MatcherAssert.assertThat(
            result[2],
            CoreMatchers.equalTo(
                EventWithMembers(event = event, members = listOf(member1, member2))
            )
        )

        job.cancel()
    }
}