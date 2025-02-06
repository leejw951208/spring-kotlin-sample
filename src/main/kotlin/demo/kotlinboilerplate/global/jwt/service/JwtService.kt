package demo.kotlinboilerplate.global.jwt.service

import demo.kotlinboilerplate.global.jwt.JwtProvider
import demo.kotlinboilerplate.auth.dto.LoginResponseDto
import demo.kotlinboilerplate.global.jwt.JwtReIssueTokenDto
import demo.kotlinboilerplate.global.redis.RedisService
import demo.kotlinboilerplate.member.persistence.repository.MemberRepository
import demo.kotlinboilerplate.member.persistence.repository.MemberRoleRepository
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

    fun reIssueJwtToken(jwtReIssueTokenDto: JwtReIssueTokenDto): LoginResponseDto {
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