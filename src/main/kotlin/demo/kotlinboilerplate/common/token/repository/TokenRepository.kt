package demo.kotlinboilerplate.common.token.repository

import demo.kotlinboilerplate.common.token.domain.Token
import java.time.LocalDateTime

interface TokenRepository {
    fun findToken(memberId: Long, refreshToken: String, issuedAt: LocalDateTime, expiredAt: LocalDateTime): Token
    fun validateToken(memberId: Long, refreshToken: String, issuedAt: LocalDateTime, expiredAt: LocalDateTime)
    fun save(token: Token)
}