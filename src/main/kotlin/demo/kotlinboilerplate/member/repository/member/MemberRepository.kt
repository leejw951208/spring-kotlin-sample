package demo.kotlinboilerplate.member.repository.member

import demo.kotlinboilerplate.member.domain.Member
import demo.kotlinboilerplate.member.domain.MemberSave

interface MemberRepository {
    fun findMember(memberId: Long): Member?
    fun existsMember(email: String): Boolean
    fun save(member: MemberSave)
    fun delete(memberId: Long)
}