package demo.kotlinboilerplate.member.repository.member

import demo.kotlinboilerplate.member.domain.Member

interface MemberRepository {
    fun findMember(memberId: Long): Member
}