package demo.kotlinboilerplate.global.jwt.controller

import demo.kotlinboilerplate.global.jwt.dto.TokenRequestDto
import demo.kotlinboilerplate.global.jwt.dto.TokenResponseDto
import demo.kotlinboilerplate.global.jwt.service.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class JwtController(
    private val jwtService: JwtService
) {
    @PostMapping("/jwt/refresh")
    fun createJwtToken(@RequestBody requestDto: TokenRequestDto): ResponseEntity<TokenResponseDto> {
        return ResponseEntity.ok(jwtService.createJwtToken(requestDto))
    }
}