package demo.kotlinboilerplate.auth.token.persistence.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import demo.kotlinboilerplate.auth.token.persistence.TokenEntity
import org.springframework.stereotype.Repository

@Repository
class TokenEntityRepositoryCustomImpl(
    private val kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor,
) : TokenEntityRepositoryCustom {
    override fun findToken(
        userId: Long,
        refreshToken: String,
    ): TokenEntity? {
        return kotlinJdslJpqlExecutor.findAll {
            select(entity(TokenEntity::class))
                .from(entity(TokenEntity::class))
                .whereAnd(
                    path(TokenEntity::userId).eq(userId),
                    path(TokenEntity::refreshToken).eq(refreshToken),
                )
        }.firstOrNull()
    }
}
