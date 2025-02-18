package demo.kotlinboilerplate.member.mapper

import demo.kotlinboilerplate.common.auth.dto.JoinRequestDto
import demo.kotlinboilerplate.member.domain.Member
import demo.kotlinboilerplate.member.domain.MemberSave
import demo.kotlinboilerplate.member.dto.MemberResponseDto
import demo.kotlinboilerplate.member.persistence.entity.MemberEntity

interface MemberMapper {
    fun toDto(member: Member): MemberResponseDto

    fun toDomain(entity: MemberEntity): Member
    fun toDomain(dto: JoinRequestDto): MemberSave

    fun toEntity(member: Member): MemberEntity
    fun toEntity(member: MemberSave): MemberEntity
}