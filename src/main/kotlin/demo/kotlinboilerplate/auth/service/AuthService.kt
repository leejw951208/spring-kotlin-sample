package demo.kotlinboilerplate.auth.service

import demo.kotlinboilerplate.auth.dto.LoginResponseDto
import demo.kotlinboilerplate.auth.dto.LoginRequestDto
import demo.kotlinboilerplate.auth.dto.JoinApproveRequestDto
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
    private val memberRoleRepository: MemberRoleRepository,
    private val jwtProvider: JwtProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val publisher: ApplicationEventPublisher,
    private val redisService: RedisService,
    private val jwtProperties: JwtProperties
) {
    @Transactional
    fun join(requestDto: JoinRequestDto) {
        val isExistsEmail = memberRepository.existsByEmail(requestDto.email)
        if (isExistsEmail) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다.")

        memberRepository.save(MemberEntity(requestDto.email, requestDto.password, requestDto.name))

        // 인증번호 redis 저장, 인증 이메일 발송
        val randomNumber = Random.Default.nextInt(100000,1000000)
        publisher.publishEvent(SignupApproveEvent(requestDto.email, randomNumber))
        publisher.publishEvent(SignupEmailSendEvent(requestDto.email, "회원가입 인증번호", "인증번호: $randomNumber"))
    }

    @Transactional
    fun joinApprove(requestDto: JoinApproveRequestDto) {
        // 인증번호로 redis에 저장된 email 조회
        val email = redisService.getValue(requestDto.approveNumber) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "인증 제한시간이 초과되었습니다.")
        val findMember = memberRepository.findByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다.")

        // 사용자 승인여부 업데이트
        findMember.isApproved(true)

        memberRoleRepository.save(MemberRoleEntity(findMember, RoleType.ROLE_USER))
    }

    @Transactional
    fun login(requestDto: LoginRequestDto): LoginResponseDto {
        val authenticationToken = UsernamePasswordAuthenticationToken(requestDto.email, requestDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        val generateToken = jwtProvider.generateToken(requestDto.email, authentication)
        redisService.setValue(authentication.name, generateToken.refreshToken, Duration.ofHours(jwtProperties.refreshTokenExpTime))

        return generateToken
    }
}