package demo.kotlinboilerplate.common.token.persistence.repository

import demo.kotlinboilerplate.common.token.persistence.entity.TokenEntity
import java.time.LocalDateTime

interface TokenEntityRepositoryCustom {
    fun findToken(memberId: Long, refreshToken: String, issuedAt: LocalDateTime, expiredAt: LocalDateTime): TokenEntity?
    fun existsToken(memberId: Long, refreshToken: String, issuedAt: LocalDateTime, expiredAt: LocalDateTime): Boolean
}