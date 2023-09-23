package demo.security.member.controller

import demo.security.member.dto.SignupCertifiedDto
import demo.security.member.dto.SignupDto
import demo.security.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signup(@Validated @RequestBody signupDto: SignupDto): ResponseEntity<String> {
        memberService.signup(signupDto)
        return ResponseEntity.ok("인증번호가 발송되었습니다. 인증 제한시간은 3분 입니다.")
    }

    @PostMapping("/signup/certified")
    fun certified(@RequestBody signupCertifiedDto: SignupCertifiedDto): ResponseEntity<String> {
        memberService.certified(signupCertifiedDto)
        return ResponseEntity.ok("인증이 완료되었습니다.")
    }
}