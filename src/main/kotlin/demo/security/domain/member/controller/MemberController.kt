package demo.security.domain.member.controller

import demo.security.domain.member.dto.SignupDto
import demo.security.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signup(@Validated @RequestBody signupDto: SignupDto): ResponseEntity<String> {
        return ResponseEntity.ok(memberService.signup(signupDto))
    }
}