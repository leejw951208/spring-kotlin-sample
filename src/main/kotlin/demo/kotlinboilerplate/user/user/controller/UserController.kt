package demo.kotlinboilerplate.user.user.controller

import demo.kotlinboilerplate.user.user.dto.UserResponseDto
import demo.kotlinboilerplate.user.user.mapper.UserMapper
import demo.kotlinboilerplate.user.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper,
) {
    @GetMapping("{id}")
    fun findUser(
        @PathVariable id: Long,
    ): UserResponseDto {
        return userMapper.toDto(userService.findUser(id))
    }
}
