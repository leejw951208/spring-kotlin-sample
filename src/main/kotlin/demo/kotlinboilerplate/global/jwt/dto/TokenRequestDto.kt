package demo.kotlinboilerplate.global.jwt.dto

data class TokenRequestDto(
    val memberId: Long?,
    val refreshToken: String?
)