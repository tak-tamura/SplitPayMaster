package com.takurotamura.splitpaymaster.domain.use_case

import com.takurotamura.splitpaymaster.data.entity.Member
import com.takurotamura.splitpaymaster.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMembersUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
) {
    fun getMembersByEventId(eventId: Int): Flow<List<Member>> =
        memberRepository.getMembersByEventId(eventId)
}