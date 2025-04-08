package demo.kotlinboilerplate.user.user.dto

import io.swagger.v3.oas.annotations.Parameter

data class UserSearchConditionDto(
    @field:Parameter(description = "이름", example = "씨엔에이아이", required = false)
    val name: String? = null,
) {
}
