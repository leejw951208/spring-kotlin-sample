package demo.kotlinboilerplate.auth.auth.dto

import demo.kotlinboilerplate.user.user.enumeration.UserStatusEnum
import demo.kotlinboilerplate.user.userrole.enumeration.UserRoleEnum
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class JoinRequestDto(
    @field:NotBlank(message = "이메일은 필수 입니다.")
    val email: String,
    @field:NotBlank(message = "비밀번호는 필수 입니다.")
    val password: String,
    @field:NotBlank(message = "이름은 필수 입니다.")
    val name: String,
    @field:NotBlank(message = "상태값은 필수입니다.")
    val status: UserStatusEnum,
    @field:NotEmpty(message = "권한은 필수입니다.")
    val userRoles: List<UserRoleEnum>,
)
