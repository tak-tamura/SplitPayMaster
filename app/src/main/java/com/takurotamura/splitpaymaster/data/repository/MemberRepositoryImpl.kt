package com.takurotamura.splitpaymaster.data.repository

import com.takurotamura.splitpaymaster.data.dao.MemberDao
import com.takurotamura.splitpaymaster.data.entity.Member
import com.takurotamura.splitpaymaster.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow

class MemberRepositoryImpl(
    private val dao: MemberDao,
) : MemberRepository {
    override suspend fun insertMember(member: Member): Long = dao.insert(member)

    override fun getMembersByEventId(eventId: Int): Flow<List<Member>> = dao.selectByEventId(eventId)
}