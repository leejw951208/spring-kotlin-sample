package demo.springkotlinsample.auth.auth.controller

import demo.springkotlinsample.auth.auth.dto.JoinRequestDto
import demo.springkotlinsample.auth.auth.dto.LoginRequestDto
import demo.springkotlinsample.auth.auth.dto.LoginResponseDto
import demo.springkotlinsample.auth.auth.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("join")
    fun join(
        @RequestBody requestDto: JoinRequestDto,
    ): String {
        authService.join(requestDto)
        return "사용자 등록 완료"
    }

    @PostMapping("login")
    fun login(
        @RequestBody requestDto: LoginRequestDto,
    ): LoginResponseDto {
        return authService.login(requestDto)
    }
}
