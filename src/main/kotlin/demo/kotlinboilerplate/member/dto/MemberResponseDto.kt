package demo.kotlinboilerplate.member.dto

import demo.kotlinboilerplate.member.domain.Member

data class MemberResponseDto(
    val id: Long?,
    val email: String?,
    val name: String?,
) {
}