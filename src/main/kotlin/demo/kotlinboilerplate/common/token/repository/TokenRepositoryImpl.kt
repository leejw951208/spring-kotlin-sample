package demo.kotlinboilerplate.common.token.repository

import demo.kotlinboilerplate.common.token.domain.Token
import demo.kotlinboilerplate.common.token.domain.TokenSave
import demo.kotlinboilerplate.common.token.mapper.TokenMapper
import demo.kotlinboilerplate.common.token.persistence.entity.TokenEntity
import demo.kotlinboilerplate.common.token.persistence.repository.TokenEntityRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Repository
class TokenRepositoryImpl(
    private val tokenMapper: TokenMapper,
    private val tokenEntityRepository: TokenEntityRepository
): TokenRepository {
    override fun findToken(memberId: Long): Token? {
        val findToken = tokenEntityRepository.findByMemberId(memberId) ?: return null
        return tokenMapper.toDomain(findToken)
    }

    override fun findToken(memberId: Long, refreshToken: String, issuedAt: LocalDateTime, expiredAt: LocalDateTime): Token? {
        val findToken = tokenEntityRepository.findToken(memberId, refreshToken, issuedAt, expiredAt) ?: return null
        return tokenMapper.toDomain(findToken)
    }

    override fun save(token: Token) {
        tokenEntityRepository.save(tokenMapper.toEntity(token))
    }

    override fun save(token: TokenSave) {
        tokenEntityRepository.save(tokenMapper.toEntity(token))
    }
}