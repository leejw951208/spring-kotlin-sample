package demo.kotlinboilerplate.auth.service

import demo.kotlinboilerplate.auth.dto.LoginResponseDto
import demo.kotlinboilerplate.auth.dto.LoginRequestDto
import demo.kotlinboilerplate.global.eventlistener.signup.SignupApproveEvent
import demo.kotlinboilerplate.global.jwt.JwtProvider
import demo.kotlinboilerplate.auth.dto.JoinRequestDto
import demo.kotlinboilerplate.global.enumeration.RoleType
import demo.kotlinboilerplate.global.eventlistener.signup.SignupEmailSendEvent
import demo.kotlinboilerplate.global.jwt.JwtProperties
import demo.kotlinboilerplate.global.redis.RedisService
import demo.kotlinboilerplate.member.persistence.entity.MemberEntity
import demo.kotlinboilerplate.member.persistence.entity.MemberRoleEntity
import demo.kotlinboilerplate.member.persistence.repository.MemberRepository
import demo.kotlinboilerplate.member.persistence.repository.MemberRoleRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.Duration
import kotlin.random.Random

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val jwtProvider: JwtProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val redisService: RedisService,
    private val jwtProperties: JwtProperties
) {
    @Transactional
    fun join(requestDto: JoinRequestDto) {
        val isExistsEmail = memberRepository.existsByEmail(requestDto.email)
        if (isExistsEmail) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다.")

        memberRepository.save(MemberEntity(requestDto.email, requestDto.password, requestDto.name))
    }

    @Transactional
    fun login(requestDto: LoginRequestDto): LoginResponseDto {
        val authenticationToken = UsernamePasswordAuthenticationToken(requestDto.email, requestDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        val createdToken = jwtProvider.createToken(authentication.name, authentication.authorities.joinToString(",") { it.authority })
        redisService.setValue(authentication.name, createdToken.refreshToken, Duration.ofHours(jwtProperties.refreshTokenExpTime))

        return LoginResponseDto.from(authentication.name.toLong(), requestDto.email, createdToken)
    }
}