package demo.security.member.controller

import demo.security.global.jwt.JwtTokenDto
import demo.security.global.security.principal.PrincipalDetails
import demo.security.member.dto.SigninRequestDto
import demo.security.member.dto.SignupVerificationDto
import demo.security.member.dto.SignupRequestDto
import demo.security.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signup(@Validated @RequestBody signupRequestDto: SignupRequestDto): ResponseEntity<String> {
        memberService.signup(signupRequestDto)
        return ResponseEntity.ok("인증번호가 발송되었습니다. 인증 제한시간은 3분 입니다.")
    }

    @PostMapping("/signup/verify")
    fun verify(@RequestBody signupVerificationDto: SignupVerificationDto): ResponseEntity<String> {
        memberService.verify(signupVerificationDto)
        return ResponseEntity.ok("인증이 완료되었습니다.")
    }

    @PostMapping("/signin")
    fun signin(@Validated @RequestBody signinRequestDto: SigninRequestDto): ResponseEntity<JwtTokenDto> {
        return ResponseEntity.ok(memberService.signin(signinRequestDto))
    }

    @GetMapping("/member/me")
    fun me(@AuthenticationPrincipal principal: PrincipalDetails): ResponseEntity<String> {
        return ResponseEntity.ok(principal.username)
    }
}