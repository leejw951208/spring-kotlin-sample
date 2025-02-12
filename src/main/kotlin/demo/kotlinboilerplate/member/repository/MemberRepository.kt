package demo.kotlinboilerplate.member.repository

import demo.kotlinboilerplate.member.domain.Member

interface MemberRepository {
    fun findById(memberId: Long): Member
}