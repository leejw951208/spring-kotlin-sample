package demo.springkotlinsample.auth.token.repository

import demo.springkotlinsample.auth.token.domain.Token
import demo.springkotlinsample.auth.token.mapper.TokenMapper
import demo.springkotlinsample.auth.token.persistence.repository.TokenEntityRepository
import org.springframework.stereotype.Repository

@Repository
class TokenRepositoryImpl(
    private val tokenEntityRepository: TokenEntityRepository,
    private val tokenMapper: TokenMapper,
) : demo.springkotlinsample.auth.token.repository.TokenRepository {
    override fun findToken(userId: Long): Token? {
        val findTokenEntity = tokenEntityRepository.findByUserId(userId) ?: return null
        return tokenMapper.toDomain(findTokenEntity)
    }

    override fun save(token: Token) {
        tokenEntityRepository.save(tokenMapper.toEntity(token))
    }
}
