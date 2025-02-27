package demo.kotlinboilerplate.user.user.mapper

import demo.kotlinboilerplate.auth.auth.dto.JoinRequestDto
import demo.kotlinboilerplate.user.user.domain.User
import demo.kotlinboilerplate.user.user.dto.UserResponseDto
import demo.kotlinboilerplate.user.user.persistence.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toDomain(entity: UserEntity): User {
        return User(entity.id, entity.email, entity.password, entity.name, entity.status)
    }

    fun toDomain(dto: JoinRequestDto): User {
        return User(null, dto.email, dto.password, dto.name, dto.status)
    }

    fun toEntity(domain: User): UserEntity {
        return UserEntity(domain.id, domain.email, domain.password, domain.name, domain.status)
    }

    fun toDto(domain: User): UserResponseDto {
        return UserResponseDto(id = domain.id, email = domain.email, name = domain.name)
    }
}
