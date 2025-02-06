package demo.kotlinboilerplate.auth.dto

data class LoginResponseDto(
    var memberId: Long,
    var email: String,
    var accessToken: String,
    var refreshToken: String
)
