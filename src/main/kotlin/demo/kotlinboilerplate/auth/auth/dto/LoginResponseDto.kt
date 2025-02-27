package demo.kotlinboilerplate.auth.auth.dto

data class LoginResponseDto(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
)
