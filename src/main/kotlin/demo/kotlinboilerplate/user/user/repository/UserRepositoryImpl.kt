package demo.kotlinboilerplate.user.user.repository

import demo.kotlinboilerplate.common.jpa.SoftDeleteFilter
import demo.kotlinboilerplate.user.user.domain.User
import demo.kotlinboilerplate.user.user.mapper.UserMapper
import demo.kotlinboilerplate.user.user.persistence.UserEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userEntityRepository: UserEntityRepository,
    private val userMapper: UserMapper,
) : UserRepository {
    @SoftDeleteFilter(true)
    override fun findOne(id: Long): User? {
        val findUserEntity = userEntityRepository.findByIdOrNull(id) ?: return null
        return userMapper.toDomain(findUserEntity)
    }

    @SoftDeleteFilter(true)
    override fun findOne(email: String): User? {
        val findUserEntity = userEntityRepository.findByEmail(email) ?: return null
        return userMapper.toDomain(findUserEntity)
    }

    override fun save(user: User): User {
        val savedUserEntity = userEntityRepository.save(userMapper.toEntity(user))
        return userMapper.toDomain(savedUserEntity)
    }
}
