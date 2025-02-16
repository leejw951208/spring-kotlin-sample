package demo.kotlinboilerplate.common.token.controller

import demo.kotlinboilerplate.common.token.service.TokenService
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController(
    private val jwtService: TokenService
) {
//    @PostMapping("/jwt/refresh")
//    fun createJwtToken(@RequestBody requestDto: TokenRequestDto): ResponseEntity<TokenResponseDto> {
//        return ResponseEntity.ok(jwtService.createJwtToken(requestDto))
//    }
}