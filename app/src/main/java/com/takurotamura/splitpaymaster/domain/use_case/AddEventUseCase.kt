package com.takurotamura.splitpaymaster.domain.use_case

import com.takurotamura.splitpaymaster.data.entity.Event
import com.takurotamura.splitpaymaster.data.entity.Member
import com.takurotamura.splitpaymaster.domain.repository.EventRepository
import com.takurotamura.splitpaymaster.domain.repository.MemberRepository
import java.time.LocalDate
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val memberRepository: MemberRepository,
) {
    suspend fun createEvent(
        eventName: String,
        eventDate: LocalDate,
        memberList: List<String>
    ) {
        val event = Event(
            eventName = eventName,
            eventDate = eventDate,
            memberCount = memberList.size
        )
        val eventId = eventRepository.insertEvent(event)

        memberList.forEach {
            memberRepository.insertMember(Member(name = it, eventId = eventId.toInt()))
        }
    }
}