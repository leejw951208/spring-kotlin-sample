package demo.security.member.service

import demo.security.member.RoleType
import demo.security.member.dto.SignupCertifiedDto
import demo.security.member.dto.SignupDto
import demo.security.member.entity.Member
import demo.security.member.entity.MemberRole
import demo.security.member.eventlistener.SignupEvent
import demo.security.member.repository.MemberRepository
import demo.security.member.repository.MemberRoleRepository
import demo.security.redis.RedisService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.lang.RuntimeException
import kotlin.random.Random

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val redisService: RedisService,
    private val eventListener: ApplicationEventPublisher
) {
    @Transactional
    fun signup(signupDto: SignupDto) {
        val findMember = memberRepository.findByEmail(signupDto.email)
        if (findMember != null) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다.")

        memberRepository.save(Member(email = signupDto.email, password = signupDto.password, name = signupDto.name))

        val randomNumber = Random.Default.nextInt(100000,1000000)
        eventListener.publishEvent(SignupEvent(signupDto.email, "회원가입 인증번호", "인증번호: $randomNumber", randomNumber))
    }

    @Transactional
    fun certified(signupCertifiedDto: SignupCertifiedDto) {
        val findMember = memberRepository.findByEmail(signupCertifiedDto.email) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 정보를 찾을 수 없습니다.")

        val value = redisService.getValue(signupCertifiedDto.email) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "인증 제한시간이 초과되었습니다.")
        if (value != signupCertifiedDto.number) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "인증번호가 다릅니다.")
        findMember.changeCertStatus(true)

        memberRoleRepository.save(MemberRole(findMember, RoleType.ROLE_USER))
    }
}