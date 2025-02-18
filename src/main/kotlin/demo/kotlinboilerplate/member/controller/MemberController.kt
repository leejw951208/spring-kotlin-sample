package demo.kotlinboilerplate.member.controller

import demo.kotlinboilerplate.common.security.principal.PrincipalDetails
import demo.kotlinboilerplate.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/member/me")
    fun me(@AuthenticationPrincipal principal: PrincipalDetails): ResponseEntity<String> {
        return ResponseEntity.ok(principal.username)
    }

    @DeleteMapping("/member/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<String> {
        memberService.deleteMember(id)
        return ResponseEntity.noContent().build()
    }
}