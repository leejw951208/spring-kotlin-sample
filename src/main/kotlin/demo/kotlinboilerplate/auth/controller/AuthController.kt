package demo.kotlinboilerplate.auth.controller

import demo.kotlinboilerplate.auth.dto.LoginResponseDto
import demo.kotlinboilerplate.auth.dto.LoginRequestDto
import demo.kotlinboilerplate.auth.dto.JoinApproveRequestDto
import demo.kotlinboilerplate.auth.service.AuthService
import demo.kotlinboilerplate.auth.dto.JoinRequestDto
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
        return ResponseEntity.ok("인증번호가 발송되었습니다. 인증 제한시간은 3분 입니다.")
    }

    @PatchMapping("/join/approval")
    fun joinApprove(@Validated @RequestBody requestDto: JoinApproveRequestDto): ResponseEntity<String> {
        authService.joinApprove(requestDto)
        return ResponseEntity.ok("가입이 완료되었습니다.")
    }

    @PostMapping("/login")
    fun login(@Validated @RequestBody requestDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        return ResponseEntity.ok(authService.login(requestDto))
    }
}