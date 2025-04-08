package demo.kotlinboilerplate.user.user.persistence.repository

import demo.kotlinboilerplate.user.user.dto.UserSearchConditionDto
import demo.kotlinboilerplate.user.user.persistence.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserEntityCustomRepository {
    fun findPage(
        pageable: Pageable,
        condition: UserSearchConditionDto,
    ): Page<UserEntity?>
}

