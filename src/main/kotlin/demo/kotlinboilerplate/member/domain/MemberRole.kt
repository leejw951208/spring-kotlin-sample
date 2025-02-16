package demo.kotlinboilerplate.member.domain

import demo.kotlinboilerplate.member.enumeration.RoleType

class MemberRole(
    val id: Long,
    var member: Member,
    var role: RoleType
) {
}