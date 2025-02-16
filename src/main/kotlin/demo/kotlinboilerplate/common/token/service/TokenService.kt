package demo.kotlinboilerplate.common.token.service

import demo.kotlinboilerplate.common.token.TokenProperties
import demo.kotlinboilerplate.common.token.repository.TokenRepository
import demo.kotlinboilerplate.common.token.TokenProvider
import demo.kotlinboilerplate.common.token.dto.TokenRequestDto
import demo.kotlinboilerplate.common.token.dto.TokenResponseDto
import demo.kotlinboilerplate.member.repository.member.MemberRepository
import demo.kotlinboilerplate.member.repository.memberrole.MemberRoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TokenService(
    private val jwtProvider: TokenProvider,
    private val jwtProperties: TokenProperties,
    private val tokenRepository: TokenRepository,
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository
) {
    @Transactional
    fun createJwtToken(requestDto: TokenRequestDto): TokenResponseDto {
        val memberId = requestDto.memberId
        val refreshToken = requestDto.refreshToken

        // token 재발급에 사용되는 refresh token 의 유효시간 검즘
        val now = LocalDateTime.now()
        val weekAgo = now.minusHours(jwtProperties.refreshTokenExpTime)
        val findToken = tokenRepository.findToken(memberId, refreshToken, weekAgo, now)

        val findMember = memberRepository.findMember(memberId)
        val findMemberRoles = memberRoleRepository.findMemberRoles(findMember.id)

        // token 재발급
        val createdToken = jwtProvider.createToken(findMember.id.toString(), findMemberRoles.joinToString(",") { it.role.name })
        findToken.refresh(refreshToken)

        return TokenResponseDto.from(createdToken)
    }
}