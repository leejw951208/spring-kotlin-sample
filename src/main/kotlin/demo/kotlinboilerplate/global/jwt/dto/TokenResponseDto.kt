package demo.kotlinboilerplate.global.jwt.dto

import demo.kotlinboilerplate.global.jwt.vo.CreateToken

data class TokenResponseDto(
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun from(token: CreateToken): TokenResponseDto {
            return TokenResponseDto(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
            )
        }
    }
}