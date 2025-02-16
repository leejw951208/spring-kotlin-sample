package demo.kotlinboilerplate.member.mapper

import demo.kotlinboilerplate.member.domain.MemberRole
import demo.kotlinboilerplate.member.persistence.entity.MemberRoleEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface MemberRoleMapper {
    fun toDomain(entities: List<MemberRoleEntity>): List<MemberRole>
}