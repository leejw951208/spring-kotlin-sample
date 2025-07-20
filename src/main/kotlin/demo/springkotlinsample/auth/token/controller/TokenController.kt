package demo.springkotlinsample.auth.token.controller

import demo.springkotlinsample.auth.token.service.TokenService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("token")
class TokenController(
    private val tokenService: TokenService,
)
