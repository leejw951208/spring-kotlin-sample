package demo.security.member.service

import demo.security.member.enumeration.RoleType
import demo.security.member.dto.SignupVerificationDto
import demo.security.member.dto.SignupRequestDto
import demo.security.member.entity.Member
import demo.security.member.entity.MemberRole
import demo.security.global.eventlistener.signup.SignupVerificationEvent
import demo.security.global.jwt.JwtProvider
import demo.security.member.dto.SignInResponseDto
import demo.security.member.repository.MemberRepository
import demo.security.member.repository.MemberRoleRepository
import demo.security.global.redis.RedisService
import demo.security.member.dto.SigninRequestDto
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
    fun signup(signupRequestDto: SignupRequestDto) {
        val findMember = memberRepository.findByEmail(signupRequestDto.email)
        if (findMember != null) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다.")

        memberRepository.save(Member(signupRequestDto.email, signupRequestDto.password, signupRequestDto.name))

        val randomNumber = Random.Default.nextInt(100000,1000000)
        publisher.publishEvent(SignupVerificationEvent(signupRequestDto.email, "회원가입 인증번호", "인증번호: $randomNumber", randomNumber))
    }

    @Transactional
    fun verify(signupVerificationDto: SignupVerificationDto) {
        val findMember = memberRepository.findByEmail(signupVerificationDto.email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다.")

        val value = redisService.getAuthNumber(signupVerificationDto.email) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "인증 제한시간이 초과되었습니다.")
        if (value != signupVerificationDto.number) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "인증번호가 다릅니다.")
        findMember.isVerified(true)

        memberRoleRepository.save(MemberRole(findMember, RoleType.ROLE_USER))
    }

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