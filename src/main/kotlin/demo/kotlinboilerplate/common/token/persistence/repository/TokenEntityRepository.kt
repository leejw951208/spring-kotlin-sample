package demo.kotlinboilerplate.common.token.persistence.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import demo.kotlinboilerplate.common.token.persistence.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TokenEntityRepository: JpaRepository<TokenEntity, Long>, TokenEntityRepositoryCustom {
}