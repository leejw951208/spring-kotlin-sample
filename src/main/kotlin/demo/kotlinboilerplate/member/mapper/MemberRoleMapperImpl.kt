package demo.kotlinboilerplate.member.mapper

import demo.kotlinboilerplate.member.domain.MemberRole
import demo.kotlinboilerplate.member.persistence.entity.MemberRoleEntity
import org.springframework.stereotype.Component

@Component
class MemberRoleMapperImpl(
    private val memberMapper: MemberMapper,
): MemberRoleMapper {
    override fun toDomain(entities: List<MemberRoleEntity>): List<MemberRole> {
        return entities.map { entity -> MemberRole(entity.id, memberMapper.toDomain(entity.member), entity.role) }
    }
}