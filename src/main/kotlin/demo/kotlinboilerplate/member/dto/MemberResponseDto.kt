package demo.kotlinboilerplate.member.dto

import demo.kotlinboilerplate.member.domain.Member

data class MemberResponseDto(
    val id: Long,
    val name: String,
    val email: String,
) {
    companion object {
        fun from(member: Member): MemberResponseDto {
            return MemberResponseDto(member.id, member.name, member.email)
        }
    }
}