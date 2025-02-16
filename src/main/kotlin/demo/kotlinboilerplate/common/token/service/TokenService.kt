package demo.kotlinboilerplate.common.token.service

import demo.kotlinboilerplate.common.token.repository.TokenRepository
import demo.kotlinboilerplate.common.token.TokenProvider
import demo.kotlinboilerplate.common.token.dto.TokenRequestDto
import demo.kotlinboilerplate.common.token.dto.TokenResponseDto
import demo.kotlinboilerplate.member.repository.member.MemberRepository
import demo.kotlinboilerplate.member.repository.memberrole.MemberRoleRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime



@Service
class TokenService(
    private val jwtProvider: TokenProvider,
    private val tokenRepository: TokenRepository,
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository
) {
    fun createJwtToken(requestDto: TokenRequestDto): TokenResponseDto {
        val memberId = requestDto.memberId
        val refreshToken = requestDto.refreshToken

        val now = LocalDateTime.now()
        val weekAgo = now.minusWeeks(1)
        tokenRepository.validateToken(memberId, refreshToken, weekAgo, now)

        val findMember = memberRepository.findMember(memberId)
        val findMemberRoles = memberRoleRepository.findMemberRoles(findMember.id)

        val createdToken = jwtProvider.createToken(findMember.id.toString(), findMemberRoles.joinToString(",") { it.role.name })

        return TokenResponseDto.from(createdToken)
    }
}