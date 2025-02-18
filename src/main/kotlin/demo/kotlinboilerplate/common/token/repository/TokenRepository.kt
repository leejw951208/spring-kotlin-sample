package demo.kotlinboilerplate.common.token.repository

import demo.kotlinboilerplate.common.token.domain.Token
import demo.kotlinboilerplate.common.token.domain.TokenSave
import java.time.LocalDateTime

interface TokenRepository {
    fun findToken(memberId: Long): Token?
    fun findToken(memberId: Long, refreshToken: String, issuedAt: LocalDateTime, expiredAt: LocalDateTime): Token?
    fun save(token: Token)
    fun save(token: TokenSave)
}