package demo.security.member.dto

data class SignInResponseDto(
    var memberId: Long?,
    var email: String?,
    var accessToken: String?,
    var refreshToken: String?
)
