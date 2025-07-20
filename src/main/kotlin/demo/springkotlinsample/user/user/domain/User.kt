package demo.springkotlinsample.user.user.domain

import demo.springkotlinsample.user.user.dto.UserPatchDto
import demo.springkotlinsample.user.user.enumeration.UserStatusEnum

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
