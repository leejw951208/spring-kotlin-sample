package demo.kotlinboilerplate.member.repository.memberrole

import demo.kotlinboilerplate.member.domain.MemberRole

interface MemberRoleRepository {
    fun findMemberRoles(memberId: Long?): List<MemberRole>
}