package demo.kotlinboilerplate.common.token.repository

import demo.kotlinboilerplate.common.token.domain.Token
import demo.kotlinboilerplate.common.token.mapper.TokenMapper
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
    override fun findToken(memberId: Long, refreshToken: String, issuedAt: LocalDateTime, expiredAt: LocalDateTime): Token {
        val findToken = tokenEntityRepository.findToken(memberId, refreshToken, issuedAt, expiredAt)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "토큰 정보를 찾을 수 없습니다.")
        return tokenMapper.toDomain(findToken)
    }

    override fun validateToken(memberId: Long, refreshToken: String, issuedAt: LocalDateTime, expiredAt: LocalDateTime) {
        val existsToken = tokenEntityRepository.existsToken(memberId, refreshToken, issuedAt, expiredAt)
        if (!existsToken) throw ResponseStatusException(HttpStatus.NOT_FOUND, "토큰 정보를 찾을 수 없습니다.")
    }
}