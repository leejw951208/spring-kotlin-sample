package demo.kotlinboilerplate.auth.controller

import demo.kotlinboilerplate.auth.service.AuthService
import demo.kotlinboilerplate.member.dto.SignupRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/signup")
    fun signup(@Validated @RequestBody signupRequestDto: SignupRequestDto): ResponseEntity<String> {
        authService.signup(signupRequestDto)
        return ResponseEntity.ok("가입 신청이 완료되었습니다.")
    }

    @PostMapping("/signup/self")
    fun signupSelf(@Validated @RequestBody signupRequestDto: SignupRequestDto): ResponseEntity<String> {
        authService.signupSelf(signupRequestDto)
        return ResponseEntity.ok("인증번호가 발송되었습니다. 인증 제한시간은 3분 입니다.")
    }

    @PatchMapping("/signup/member/{memberId}")
    fun approvedUpdate(@PathVariable memberId: Long) {

    }
}