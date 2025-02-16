package demo.kotlinboilerplate.common.auth.controller

import demo.kotlinboilerplate.common.auth.dto.LoginResponseDto
import demo.kotlinboilerplate.common.auth.dto.LoginRequestDto
import demo.kotlinboilerplate.common.auth.service.AuthService
import demo.kotlinboilerplate.common.auth.dto.JoinRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/join")
    fun join(@Validated @RequestBody requestDto: JoinRequestDto): ResponseEntity<String> {
        authService.join(requestDto)
        return ResponseEntity.ok("가입이 완료되었습니다.")
    }
    @PostMapping("/login")
    fun login(@Validated @RequestBody requestDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        return ResponseEntity.ok(authService.login(requestDto))
    }
}