package demo.springkotlinsample.user.userrole.mapper

import demo.springkotlinsample.auth.auth.dto.JoinRequestDto
import demo.springkotlinsample.user.user.domain.User
import demo.springkotlinsample.user.userrole.domain.UserRole
import demo.springkotlinsample.user.userrole.enumeration.UserRoleEnum
import demo.springkotlinsample.user.userrole.persistence.UserRoleEntity
import org.springframework.stereotype.Component

@Component
class UserRoleMapper {
    fun toDomain(entities: List<UserRoleEntity>): List<UserRole> {
        return entities.map { UserRole(it.id, it.userId, it.role) }
    }

    fun toDomain(
        dto: JoinRequestDto,
        user: User,
    ): List<UserRole> {
        return dto.roles.map { roleEnum: UserRoleEnum -> UserRole(null, user.id!!, roleEnum) }
    }

    fun toEntity(roles: List<UserRole>): List<UserRoleEntity> {
        return roles.map { role -> UserRoleEntity(null, role.userId, role.role) }
    }

    fun toEntity(role: UserRole): UserRoleEntity {
        return UserRoleEntity(null, role.userId, role.role)
    }
}
