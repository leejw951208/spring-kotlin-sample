package demo.kotlinboilerplate.auth.token.mapper

import demo.kotlinboilerplate.auth.token.domain.Token
import demo.kotlinboilerplate.auth.token.persistence.TokenEntity
import org.springframework.stereotype.Component

@Component
class TokenMapper {
    fun toDomain(entity: TokenEntity): Token {
        return Token(entity.id, entity.userId, entity.refreshToken)
    }

    fun toDomain(
        userId: Long,
        refreshToken: String,
    ): Token {
        return Token(null, userId, refreshToken)
    }

    fun toEntity(token: Token): TokenEntity {
        return TokenEntity(token.id, token.userId, token.refreshToken)
    }
}
