package demo.springkotlinsample.user.userrole.domain

import demo.springkotlinsample.user.userrole.enumeration.UserRoleEnum

class UserRole(
    val id: Long?,
    var userId: Long,
    var role: UserRoleEnum,
)
