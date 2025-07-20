package demo.springkotlinsample.auth.token.persistence.repository

import demo.springkotlinsample.auth.token.persistence.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenEntityRepository : JpaRepository<TokenEntity, Long>, TokenEntityRepositoryCustom {
    fun findByUserId(userId: Long): TokenEntity?
}
