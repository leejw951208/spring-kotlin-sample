package demo.kotlinboilerplate.common.token.mapper

import demo.kotlinboilerplate.common.token.domain.Token
import demo.kotlinboilerplate.common.token.domain.TokenSave
import demo.kotlinboilerplate.common.token.persistence.entity.TokenEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TokenMapperImpl: TokenMapper {
    override fun toDomain(entity: TokenEntity): Token {
        return Token(entity.id, entity.memberId, entity.refreshToken, entity.issuedAt)
    }

    override fun toDomain(memberId: Long, refreshToken: String, dateTime: LocalDateTime): TokenSave {
        return TokenSave(memberId, refreshToken, dateTime)
    }

    override fun toEntity(domain: Token): TokenEntity {
        return TokenEntity(domain.id, domain.memberId, domain.refreshToken, domain.issuedAt)
    }

    override fun toEntity(domain: TokenSave): TokenEntity {
        return TokenEntity(null, domain.memberId, domain.refreshToken, domain.issuedAt)
    }
}