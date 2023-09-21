package demo.security.domain.member.service

import demo.security.domain.member.dto.SignupDto
import demo.security.domain.member.entity.Member
import demo.security.domain.member.eventlistener.SignupEvent
import demo.security.domain.member.repository.MemberRepository
import demo.security.domain.member.repository.MemberRoleRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val eventListener: ApplicationEventPublisher
) {
    @Transactional
    fun signup(signupDto: SignupDto): String {
        memberRepository.save(Member(email = signupDto.email, password = signupDto.password, name = signupDto.name))
        val random = Random.Default
        eventListener.publishEvent(SignupEvent(signupDto.email, "회원가입 인증번호", "인증번호: " + random.nextInt(1000000)))
        return "인증번호가 발송되었습니다.";
    }
}