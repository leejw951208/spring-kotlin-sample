package demo.kotlinboilerplate.common.token.dto

import demo.kotlinboilerplate.common.token.vo.CreateToken

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