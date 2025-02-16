package demo.kotlinboilerplate.member.service

import demo.kotlinboilerplate.member.dto.MemberResponseDto
import demo.kotlinboilerplate.member.mapper.MemberMapper
import demo.kotlinboilerplate.member.repository.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberMapper: MemberMapper,
) {
    @Transactional(readOnly = true)
    fun findMember(memberId: Long): MemberResponseDto {
        val findMember = memberRepository.findMember(memberId)
        return memberMapper.toDto(findMember)
    }
}