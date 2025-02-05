package demo.kotlinboilerplate.auth.dto

data class SignInResponseDto(
    var memberId: Long?,
    var email: String?,
    var accessToken: String?,
    var refreshToken: String?
)
