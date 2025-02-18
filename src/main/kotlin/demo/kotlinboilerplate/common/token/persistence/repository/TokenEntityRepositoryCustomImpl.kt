package demo.kotlinboilerplate.common.token.persistence.repository

import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import demo.kotlinboilerplate.common.token.persistence.entity.TokenEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TokenEntityRepositoryCustomImpl(
    private val kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor
): TokenEntityRepositoryCustom {
    override fun findToken(memberId: Long, refreshToken: String, issuedAt: LocalDateTime, expiredAt: LocalDateTime): TokenEntity? {
        return kotlinJdslJpqlExecutor.findAll {
            select(entity(TokenEntity::class))
                .from(entity(TokenEntity::class))
                .whereAnd(
                    path(TokenEntity::memberId).eq(memberId),
                    path(TokenEntity::refreshToken).eq(refreshToken),
                    path(TokenEntity::issuedAt).between(issuedAt, expiredAt)
                )
        }.firstOrNull()
    }
}