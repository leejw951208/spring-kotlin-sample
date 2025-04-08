package demo.kotlinboilerplate.user.user.service

import demo.kotlinboilerplate.common.exception.BaseException
import demo.kotlinboilerplate.common.exception.ExceptionEnum
import demo.kotlinboilerplate.common.pageable.PageableDto
import demo.kotlinboilerplate.user.user.domain.User
import demo.kotlinboilerplate.user.user.dto.UserPatchDto
import demo.kotlinboilerplate.user.user.dto.UserResponseDto
import demo.kotlinboilerplate.user.user.dto.UserSearchConditionDto
import demo.kotlinboilerplate.user.user.mapper.UserMapper
import demo.kotlinboilerplate.user.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {
    fun findOne(id: Long): User {
        return userRepository.findOne(id) ?: throw BaseException(ExceptionEnum.NOT_FOUND_USER, this::class.java.name, null)
    }

    fun findPage(
        page: Int,
        size: Int,
        order: String,
        orderBy: String,
        condition: UserSearchConditionDto,
    ): PageableDto<List<UserResponseDto>> {
        val findUserList = userRepository.findAll(page, size, order, orderBy, condition)
        return PageableDto(userMapper.toDto(findUserList), page, size, findUserList.size)
    }

    @Transactional
    fun update(
        id: Long,
        patchDto: UserPatchDto,
    ) {
        val findUser =
            userRepository.findOne(id)
                ?: throw BaseException(ExceptionEnum.NOT_FOUND_USER, this::class.java.name, null)
        findUser.update(patchDto)
        userRepository.save(findUser)
    }

    @Transactional
    fun delete(id: Long) {
        userRepository.delete(id)
    }
}
