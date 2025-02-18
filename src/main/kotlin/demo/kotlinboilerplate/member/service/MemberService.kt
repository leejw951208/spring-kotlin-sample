package demo.kotlinboilerplate.member.service

import demo.kotlinboilerplate.member.mapper.MemberMapper
import demo.kotlinboilerplate.member.repository.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberMapper: MemberMapper,
) {
    @Transactional
    fun deleteMember(memberId: Long) {
        memberRepository.delete(memberId)
    }
}