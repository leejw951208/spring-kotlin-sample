package demo.kotlinboilerplate.global.jwt.service

import demo.kotlinboilerplate.global.jwt.JwtProvider
import demo.kotlinboilerplate.global.jwt.dto.TokenRequestDto
import demo.kotlinboilerplate.global.jwt.dto.TokenResponseDto
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
    fun createJwtToken(requestDto: TokenRequestDto): TokenResponseDto {
        val memberId = requestDto.memberId
        val refreshToken = requestDto.refreshToken

        val findRefreshToken = redisService.getValue(memberId.toString()) ?: throw ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "만료된 토큰입니다. 다시 로그인 해주세요"
        )
        if (findRefreshToken != refreshToken) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "토큰을 재발급 받을 수 없습니다.")

        val findMember = memberRepository.findByIdOrNull(memberId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "사용자 정보를 찾을 수 없습니다."
        )
        val findMemberRoles = memberRoleRepository.findByMember(findMember)

        val createdToken = jwtProvider.createToken(findMember.id.toString(), findMemberRoles.joinToString(",") { it.role.name })
        redisService.setValue(findMember.id.toString(), createdToken.refreshToken)

        return TokenResponseDto.from(createdToken)
    }
}