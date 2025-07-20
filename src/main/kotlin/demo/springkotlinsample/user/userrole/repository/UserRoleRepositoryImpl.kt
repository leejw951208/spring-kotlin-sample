package demo.springkotlinsample.user.userrole.repository

import demo.springkotlinsample.user.userrole.domain.UserRole
import demo.springkotlinsample.user.userrole.mapper.UserRoleMapper
import demo.springkotlinsample.user.userrole.persistence.UserRoleEntityRepository
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
