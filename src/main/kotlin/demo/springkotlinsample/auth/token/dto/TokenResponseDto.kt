package demo.springkotlinsample.auth.token.dto

data class TokenResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
