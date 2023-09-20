package demo.security.domain.dto

import jakarta.validation.constraints.*

data class SignupDto(
    @field:NotBlank(message = "이메일은 필수 입니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수 입니다.")
    val password: String,

    @field:NotBlank(message = "이름은 필수 입니다.")
    val name: String,
    val age: Int?,
    val birthDay: String?
)
