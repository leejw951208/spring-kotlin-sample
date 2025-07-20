package demo.springkotlinsample.auth.token.persistence.repository

import demo.springkotlinsample.auth.token.persistence.TokenEntity

interface TokenEntityRepositoryCustom {
    fun findToken(
        userId: Long,
        refreshToken: String,
    ): TokenEntity?
}
