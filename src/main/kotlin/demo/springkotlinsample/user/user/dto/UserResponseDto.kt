package demo.springkotlinsample.user.user.dto

import demo.springkotlinsample.user.user.enumeration.UserStatusEnum
import io.swagger.v3.oas.annotations.media.Schema

data class UserResponseDto(
    @Schema(description = "사용자 ID", example = "1")
    val id: Long?,
    @Schema(description = "아이디", example = "test@gmail.com")
    val loginId: String?,
    @Schema(description = "이름", example = "이진우")
    val name: String?,
    @Schema(description = "계정 상태", example = "ACTIVE")
    val status: UserStatusEnum,
)
