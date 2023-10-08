package demo.security.global.jwt.service

import demo.security.global.jwt.JwtProvider
import demo.security.member.dto.SignInResponseDto
import demo.security.global.jwt.JwtReIssueTokenDto
import demo.security.global.redis.RedisService
import demo.security.member.repository.MemberRepository
import demo.security.member.repository.MemberRoleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class JwtService(
    private val jwtProvider: JwtProvider,
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val redisService: RedisService
) {

    fun reIssueJwtToken(jwtReIssueTokenDto: JwtReIssueTokenDto): SignInResponseDto {
        val findRefreshToken = redisService.getRefreshToken(jwtReIssueTokenDto.memberId.toString()) ?: throw ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "expired refresh token, please login"
        )
        if (findRefreshToken != jwtReIssueTokenDto.refreshToken) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "not equals refresh token")

        jwtProvider.verify(jwtReIssueTokenDto.refreshToken)

        val findMember = memberRepository.findByIdOrNull(jwtReIssueTokenDto.memberId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "not found member"
        )
        val findMemberRoles = memberRoleRepository.findByMember(findMember)

        val generateToken = jwtProvider.generateToken(findMember.id.toString(), findMemberRoles.joinToString(",") { it.role.name })
        generateToken.email = findMember.email

        redisService.setRefreshToken(findMember.id.toString(), generateToken.refreshToken)

        return generateToken
    }
}