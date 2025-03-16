package demo.kotlinboilerplate.user.user.dto

import io.swagger.v3.oas.annotations.media.Schema

data class UserResponseDto(
    @Schema(description = "사용자 ID", example = "1")
    val id: Long?,
    @Schema(description = "이메일", example = "test@test.com")
    val email: String?,
    @Schema(description = "이름", example = "테스트")
    val name: String?,
)
