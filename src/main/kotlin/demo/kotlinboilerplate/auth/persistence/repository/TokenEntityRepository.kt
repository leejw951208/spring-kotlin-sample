package demo.kotlinboilerplate.auth.persistence.repository

import demo.kotlinboilerplate.auth.persistence.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenEntityRepository: JpaRepository<TokenEntity, Long> {
}