package demo.kotlinboilerplate.auth.dto

import demo.kotlinboilerplate.global.jwt.vo.CreateToken

data class LoginResponseDto(
    var memberId: Long,
    var email: String,
    var accessToken: String,
    var refreshToken: String
) {
    companion object {
        fun from(memberId: Long, email: String, token: CreateToken): LoginResponseDto {
            return LoginResponseDto(
                memberId = memberId,
                email = email,
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
            )
        }
    }
}
