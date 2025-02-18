package demo.kotlinboilerplate.common.token.mapper

import demo.kotlinboilerplate.common.token.domain.Token
import demo.kotlinboilerplate.common.token.domain.TokenSave
import demo.kotlinboilerplate.common.token.persistence.entity.TokenEntity
import java.time.LocalDateTime

interface TokenMapper {
    fun toDomain(entity: TokenEntity): Token
    fun toDomain(memberId: Long, refreshToken: String, dateTime: LocalDateTime): TokenSave

    fun toEntity(domain: Token): TokenEntity
    fun toEntity(domain: TokenSave): TokenEntity
}