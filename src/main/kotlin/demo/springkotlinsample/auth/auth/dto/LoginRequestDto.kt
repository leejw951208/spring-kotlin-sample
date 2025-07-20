package demo.springkotlinsample.auth.auth.dto

import jakarta.validation.constraints.NotBlank

data class LoginRequestDto(
    @field:NotBlank(message = "이메일은 필수 입니다.")
    val email: String,
    @field:NotBlank(message = "비밀번호는 필수 입니다.")
    val password: String,
)
