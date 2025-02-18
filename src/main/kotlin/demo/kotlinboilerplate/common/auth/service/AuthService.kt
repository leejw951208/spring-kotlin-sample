package demo.kotlinboilerplate.common.auth.service

import demo.kotlinboilerplate.common.auth.dto.LoginResponseDto
import demo.kotlinboilerplate.common.auth.dto.LoginRequestDto
import demo.kotlinboilerplate.common.token.TokenProvider
import demo.kotlinboilerplate.common.auth.dto.JoinRequestDto
import demo.kotlinboilerplate.common.token.domain.Token
import demo.kotlinboilerplate.common.token.mapper.TokenMapper
import demo.kotlinboilerplate.common.token.repository.TokenRepository
import demo.kotlinboilerplate.member.mapper.MemberMapper
import demo.kotlinboilerplate.member.repository.member.MemberRepository
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val jwtProvider: TokenProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val tokenRepository: TokenRepository,
    private val memberMapper: MemberMapper,
    private val tokenMapper: TokenMapper
) {
    @Transactional
    fun join(dto: JoinRequestDto) {
        val existsMember = memberRepository.existsMember(dto.email)
        if (existsMember) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일 입니다.")

        memberRepository.save(memberMapper.toDomain(dto))
    }

    @Transactional
    fun login(dto: LoginRequestDto): LoginResponseDto {
        val authenticationToken = UsernamePasswordAuthenticationToken(dto.email, dto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        val createdToken = jwtProvider.createToken(authentication.name, authentication.authorities.joinToString(",") { it.authority })

        val memberId = authentication.name.toLong()
        val refreshToken = createdToken.refreshToken

        val findToken = tokenRepository.findToken(memberId)
        if (findToken != null) {
            findToken.refresh(refreshToken, LocalDateTime.now())
            tokenRepository.save(findToken)
        } else {
            tokenRepository.save(tokenMapper.toDomain(memberId, refreshToken, LocalDateTime.now()))
        }

        return LoginResponseDto.from(authentication.name.toLong(), dto.email, createdToken)
    }
}