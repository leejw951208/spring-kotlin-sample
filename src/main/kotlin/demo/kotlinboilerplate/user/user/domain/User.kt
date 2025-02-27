package demo.kotlinboilerplate.user.user.domain

import demo.kotlinboilerplate.user.user.enumeration.UserStatusEnum

class User(
    val id: Long?,
    var email: String,
    var password: String,
    var name: String,
    var status: UserStatusEnum,
)
