package demo.kotlinboilerplate.common.token.dto

data class TokenRequestDto(
    val memberId: Long,
    val refreshToken: String
)