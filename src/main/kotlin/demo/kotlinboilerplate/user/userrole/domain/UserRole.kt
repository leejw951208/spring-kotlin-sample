package demo.kotlinboilerplate.user.userrole.domain

import demo.kotlinboilerplate.user.userrole.enumeration.UserRoleEnum

class UserRole(
    val id: Long?,
    var userId: Long,
    var role: UserRoleEnum,
)
