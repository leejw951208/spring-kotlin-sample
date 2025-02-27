package demo.kotlinboilerplate.user.user.service

import demo.kotlinboilerplate.common.exception.BaseException
import demo.kotlinboilerplate.common.exception.ExceptionEnum
import demo.kotlinboilerplate.user.user.domain.User
import demo.kotlinboilerplate.user.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findUser(id: Long): User {
        return userRepository.findUser(id) ?: throw BaseException(ExceptionEnum.NOT_FOUND_USER, this::class.java.name, null)
    }
}
