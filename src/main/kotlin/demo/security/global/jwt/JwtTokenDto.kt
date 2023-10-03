package demo.security.global.jwt

data class JwtTokenDto(
    val accessToken: String,
    val refreshToken: String
)
