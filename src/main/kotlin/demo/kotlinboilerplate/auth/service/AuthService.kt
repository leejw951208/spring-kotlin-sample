package demo.kotlinboilerplate.auth.service

import demo.kotlinboilerplate.global.eventlistener.signup.SignupVerificationEvent
import demo.kotlinboilerplate.global.jwt.JwtProvider
import demo.kotlinboilerplate.member.dto.SignupRequestDto
import demo.kotlinboilerplate.member.persistence.entity.MemberEntity
import demo.kotlinboilerplate.member.persistence.repository.MemberRepository
import demo.kotlinboilerplate.member.persistence.repository.MemberRoleRepository
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import kotlin.random.Random

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val jwtProvider: JwtProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {
    @Transactional
    fun signup(signupRequestDto: SignupRequestDto) {
        val isExistsEmail = memberRepository.existsByEmail(signupRequestDto.email)
        if (isExistsEmail) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다.")

        memberRepository.save(MemberEntity(signupRequestDto.email, signupRequestDto.password, signupRequestDto.name))

    }

    @Transactional
    fun signupSelf(signupRequestDto: SignupRequestDto) {
        val findMember = memberRepository.findByEmail(signupRequestDto.email)
        if (findMember != null) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다.")

        memberRepository.save(MemberEntity(signupRequestDto.email, signupRequestDto.password, signupRequestDto.name))

        val randomNumber = Random.Default.nextInt(100000,1000000)
        publisher.publishEvent(SignupVerificationEvent(signupRequestDto.email, "회원가입 인증번호", "인증번호: $randomNumber", randomNumber))
    }
}