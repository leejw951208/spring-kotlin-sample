package demo.kotlinboilerplate.member.mapper

import demo.kotlinboilerplate.member.domain.Member
import demo.kotlinboilerplate.member.dto.MemberResponseDto
import demo.kotlinboilerplate.member.persistence.entity.MemberEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface MemberMapper {
    fun toDto(member: Member): MemberResponseDto
    fun toDomain(entity: MemberEntity): Member
}