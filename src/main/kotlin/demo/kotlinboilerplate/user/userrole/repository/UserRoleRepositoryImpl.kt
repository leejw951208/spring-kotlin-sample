package demo.kotlinboilerplate.user.userrole.repository

import demo.kotlinboilerplate.user.userrole.domain.UserRole
import demo.kotlinboilerplate.user.userrole.mapper.UserRoleMapper
import demo.kotlinboilerplate.user.userrole.persistence.UserRoleEntityRepository
import org.springframework.stereotype.Repository

@Repository
class UserRoleRepositoryImpl(
    private val userRoleMapper: UserRoleMapper,
    private val userRoleEntityRepository: UserRoleEntityRepository,
) : UserRoleRepository {
    override fun findUserRoles(userId: Long): List<UserRole> {
        return userRoleMapper.toDomain(userRoleEntityRepository.findAllByUserId(userId))
    }

    override fun save(userRoles: List<UserRole>) {
        userRoleEntityRepository.saveAll(userRoleMapper.toEntity(userRoles))
    }
}
