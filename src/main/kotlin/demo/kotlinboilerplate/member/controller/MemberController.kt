package demo.kotlinboilerplate.member.controller

import demo.kotlinboilerplate.auth.dto.LoginResponseDto
import demo.kotlinboilerplate.global.security.principal.PrincipalDetails
import demo.kotlinboilerplate.auth.dto.LoginRequestDto
import demo.kotlinboilerplate.member.service.MemberService
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
    @GetMapping("/member/me")
    fun me(@AuthenticationPrincipal principal: PrincipalDetails): ResponseEntity<String> {
        return ResponseEntity.ok(principal.username)
    }
}