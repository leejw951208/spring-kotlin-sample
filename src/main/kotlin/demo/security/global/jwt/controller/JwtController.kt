package demo.security.global.jwt.controller

import demo.security.member.dto.SignInResponseDto
import demo.security.global.jwt.JwtReIssueTokenDto
import demo.security.global.jwt.service.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class JwtController(
    private val jwtService: JwtService
) {
    @PostMapping("/jwt/refresh")
    fun reIssueJwtToken(@RequestBody jwtReIssueTokenDto: JwtReIssueTokenDto): ResponseEntity<SignInResponseDto> {
        return ResponseEntity.ok(jwtService.reIssueJwtToken(jwtReIssueTokenDto))
    }
}