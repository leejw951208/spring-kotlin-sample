package demo.kotlinboilerplate.auth.token.dto

data class TokenRequestDto(
    val userId: Long,
    val refreshToken: String,
)
