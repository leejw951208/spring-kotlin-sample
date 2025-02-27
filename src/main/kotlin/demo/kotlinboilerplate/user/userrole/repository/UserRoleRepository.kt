package demo.kotlinboilerplate.user.userrole.repository

import demo.kotlinboilerplate.user.userrole.domain.UserRole

interface UserRoleRepository {
    fun findUserRoles(userId: Long): List<UserRole>

    fun save(userRoles: List<UserRole>)
}
