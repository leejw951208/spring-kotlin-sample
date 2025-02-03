package demo.kotlinboilerplate.global.jwt

data class JwtReIssueTokenDto(
    val memberId: Long?,
    val refreshToken: String?
)