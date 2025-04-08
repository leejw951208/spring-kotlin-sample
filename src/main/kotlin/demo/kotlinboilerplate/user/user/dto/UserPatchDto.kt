package demo.kotlinboilerplate.user.user.dto

import demo.kotlinboilerplate.user.user.enumeration.UserStatusEnum
import io.swagger.v3.oas.annotations.media.Schema

data class UserPatchDto(
    @Schema(description = "이름", example = "이진우", required = false)
    val name: String? = null,
    @Schema(description = "아이디", example = "test@gmail.com", required = false)
    val loginId: String? = null,
    @Schema(description = "계정 상태", example = "ACTIVE", required = false)
    val status: UserStatusEnum? = null,
) {
}
