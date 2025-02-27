package demo.kotlinboilerplate.auth.token.persistence.repository

import demo.kotlinboilerplate.auth.token.persistence.TokenEntity

interface TokenEntityRepositoryCustom {
    fun findToken(
        userId: Long,
        refreshToken: String,
    ): TokenEntity?
}
