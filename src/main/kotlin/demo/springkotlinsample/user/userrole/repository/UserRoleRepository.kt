package demo.springkotlinsample.user.userrole.repository

import demo.springkotlinsample.user.userrole.domain.UserRole

interface UserRoleRepository {
    fun findUserRoles(userId: Long): List<UserRole>

    fun save(userRoles: List<UserRole>)
}
