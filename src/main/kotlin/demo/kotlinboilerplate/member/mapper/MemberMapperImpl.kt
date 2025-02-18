package demo.kotlinboilerplate.member.mapper

import demo.kotlinboilerplate.common.auth.dto.JoinRequestDto
import demo.kotlinboilerplate.member.domain.Member
import demo.kotlinboilerplate.member.domain.MemberSave
import demo.kotlinboilerplate.member.dto.MemberResponseDto
import demo.kotlinboilerplate.member.persistence.entity.MemberEntity
import org.springframework.stereotype.Component

@Component
class MemberMapperImpl: MemberMapper {
    override fun toDto(member: Member): MemberResponseDto {
        return MemberResponseDto(member.id, member.email, member.name)
    }

    override fun toDomain(entity: MemberEntity): Member {
        return Member(entity.id, entity.email, entity.password, entity.name)
    }

    override fun toDomain(dto: JoinRequestDto): MemberSave {
        return MemberSave(dto.email, dto.password, dto.name)
    }

    override fun toEntity(member: Member): MemberEntity {
        return MemberEntity(member.id, member.email, member.password, member.name)
    }

    override fun toEntity(member: MemberSave): MemberEntity {
        return MemberEntity(null, member.email, member.password, member.name)
    }
}