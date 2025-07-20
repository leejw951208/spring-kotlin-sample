package demo.springkotlinsample.user.user.mapper

import demo.springkotlinsample.auth.auth.dto.JoinRequestDto
import demo.springkotlinsample.user.user.domain.User
import demo.springkotlinsample.user.user.dto.UserResponseDto
import demo.springkotlinsample.user.user.persistence.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toDomain(entity: UserEntity): User {
        return User(
            entity.id,
            entity.email,
            entity.password,
            entity.name,
            entity.status,
        )
    }

    fun toDomain(dto: JoinRequestDto): User {
        return User(null, dto.loginId, dto.password, dto.name, dto.status)
    }

    fun toDomain(entityList: List<UserEntity?>): List<User> {
        return entityList.filterNotNull().map { entity ->
            User(
                entity.id,
                entity.email,
                entity.password,
                entity.name,
                entity.status,
            )
        }
    }

    fun toEntity(user: User): UserEntity {
        return UserEntity(user.id, user.loginId, user.password, user.name, user.status)
    }

    fun toDto(user: User): UserResponseDto {
        return UserResponseDto(user.id, user.loginId, user.name, user.status)
    }

    fun toDto(userList: List<User>): List<UserResponseDto> {
        return userList.map {
                user ->
            UserResponseDto(user.id, user.loginId, user.name, user.status)
        }
    }
}
