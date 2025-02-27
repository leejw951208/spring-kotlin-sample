package demo.kotlinboilerplate.auth.token.repository

import demo.kotlinboilerplate.auth.token.domain.Token

interface TokenRepository {
    fun findToken(userId: Long): Token?

    fun save(token: Token)
}
