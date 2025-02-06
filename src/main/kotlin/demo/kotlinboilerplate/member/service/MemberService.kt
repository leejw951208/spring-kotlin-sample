package demo.kotlinboilerplate.member.service

import demo.kotlinboilerplate.global.jwt.JwtProvider
import demo.kotlinboilerplate.auth.dto.LoginResponseDto
import demo.kotlinboilerplate.member.persistence.repository.MemberRepository
import demo.kotlinboilerplate.member.persistence.repository.MemberRoleRepository
import demo.kotlinboilerplate.global.redis.RedisService
import demo.kotlinboilerplate.auth.dto.LoginRequestDto
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository
) {
    @Transactional(readOnly = true)
    fun findMember() {

    }
}