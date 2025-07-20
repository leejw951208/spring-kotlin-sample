package demo.springkotlinsample.auth.token.repository

import demo.springkotlinsample.auth.token.domain.Token

interface TokenRepository {
    fun findToken(userId: Long): Token?

    fun save(token: Token)
}
