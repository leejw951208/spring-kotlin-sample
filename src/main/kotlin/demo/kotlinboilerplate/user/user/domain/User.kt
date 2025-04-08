package demo.kotlinboilerplate.user.user.domain

import demo.kotlinboilerplate.user.user.dto.UserPatchDto
import demo.kotlinboilerplate.user.user.enumeration.UserStatusEnum

class User(
    val id: Long?,
    var loginId: String,
    var password: String,
    var name: String,
    var status: UserStatusEnum,
) {
    fun update(dto: UserPatchDto) {
        dto.name?.let { this.name = it }
        dto.status?.let { this.status = it }
    }
}
