package demo.kotlinboilerplate.member.service

import demo.kotlinboilerplate.global.enumeration.RoleType
import demo.kotlinboilerplate.auth.dto.SignupApproveDto
import demo.kotlinboilerplate.auth.dto.SignupRequestDto
import demo.kotlinboilerplate.member.persistence.entity.MemberEntity
import demo.kotlinboilerplate.member.persistence.entity.MemberRoleEntity
import demo.kotlinboilerplate.global.eventlistener.signup.SignupApproveEvent
import demo.kotlinboilerplate.global.jwt.JwtProvider
import demo.kotlinboilerplate.auth.dto.SignInResponseDto
import demo.kotlinboilerplate.member.persistence.repository.MemberRepository
import demo.kotlinboilerplate.member.persistence.repository.MemberRoleRepository
import demo.kotlinboilerplate.global.redis.RedisService
import demo.kotlinboilerplate.auth.dto.SigninRequestDto
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import kotlin.random.Random

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val redisService: RedisService,
    private val publisher: ApplicationEventPublisher,
    private val jwtProvider: JwtProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {
    @Transactional
    fun signin(signinRequestDto: SigninRequestDto): SignInResponseDto {
        val authenticationToken = UsernamePasswordAuthenticationToken(signinRequestDto.email, signinRequestDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        val generateToken = jwtProvider.generateToken(authentication.name, authentication.authorities.joinToString(",") { it.authority })
        generateToken.email = signinRequestDto.email

        redisService.setRefreshToken(authentication.name, generateToken.refreshToken)

        return generateToken
    }
}