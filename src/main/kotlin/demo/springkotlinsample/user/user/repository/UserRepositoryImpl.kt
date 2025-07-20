package demo.springkotlinsample.user.user.repository

import demo.springkotlinsample.common.jpa.SoftDeleteFilter
import demo.springkotlinsample.common.pageable.PageableUtil
import demo.springkotlinsample.user.user.domain.User
import demo.springkotlinsample.user.user.dto.UserSearchConditionDto
import demo.springkotlinsample.user.user.mapper.UserMapper
import demo.springkotlinsample.user.user.persistence.repository.UserEntityRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userEntityRepository: UserEntityRepository,
    private val userMapper: UserMapper,
) : UserRepository {
    @SoftDeleteFilter(true)
    override fun findOne(id: Long): User? {
        return userMapper.toDomain(userEntityRepository.findByIdOrNull(id) ?: return null)
    }

    @SoftDeleteFilter(true)
    override fun findOne(loginId: String): User? {
        return userMapper.toDomain(userEntityRepository.findByEmail(loginId) ?: return null)
    }

    @SoftDeleteFilter(true)
    override fun findOne(
        loginId: String,
        password: String,
    ): User? {
        return userMapper.toDomain(userEntityRepository.findByEmailAndPassword(loginId, password) ?: return null)
    }

    @SoftDeleteFilter(true)
    override fun findAll(
        page: Int,
        size: Int,
        order: String,
        orderBy: String,
        condition: UserSearchConditionDto,
    ): List<User> {
        val pageable = PageableUtil.createPageable(page, size, order, orderBy)
        val findUserEntityPage = userEntityRepository.findPage(pageable, condition)
        val content = findUserEntityPage.content
        if (content.isEmpty()) return emptyList()
        return userMapper.toDomain(content)
    }

    override fun save(user: User): User {
        return userMapper.toDomain(userEntityRepository.save(userMapper.toEntity(user)))
    }

    override fun delete(id: Long) {
        userEntityRepository.findByIdOrNull(id)?.delete()
    }
}
