package demo.springkotlinsample.user.user.persistence.repository

import demo.springkotlinsample.user.user.dto.UserSearchConditionDto
import demo.springkotlinsample.user.user.persistence.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserEntityCustomRepository {
    fun findPage(
        pageable: Pageable,
        condition: UserSearchConditionDto,
    ): Page<UserEntity?>
}

