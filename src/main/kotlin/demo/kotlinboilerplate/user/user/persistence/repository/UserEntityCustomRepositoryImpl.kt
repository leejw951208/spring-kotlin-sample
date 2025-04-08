package demo.kotlinboilerplate.user.user.persistence.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import demo.kotlinboilerplate.user.user.dto.UserSearchConditionDto
import demo.kotlinboilerplate.user.user.persistence.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class UserEntityCustomRepositoryImpl(
    private val kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor,
) : UserEntityCustomRepository {
    override fun findPage(
        pageable: Pageable,
        condition: UserSearchConditionDto,
    ): Page<UserEntity?> {
        return kotlinJdslJpqlExecutor.findPage(pageable) {
            select(entity(UserEntity::class))
                .from(entity(UserEntity::class))
                .where(
                    condition.name?.let { path(UserEntity::name).like("%$it%") },
                )
        }
    }
}

