package com.takurotamura.splitpaymaster.domain.repository

import com.takurotamura.splitpaymaster.data.entity.Member
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun insertMember(member: Member): Long

    fun getMembersByEventId(eventId: Int): Flow<List<Member>>
}