package demo.springkotlinsample.auth.auth.dto

data class LoginResponseDto(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
)
