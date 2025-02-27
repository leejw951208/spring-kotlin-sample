package demo.kotlinboilerplate.auth.auth.service

import demo.kotlinboilerplate.auth.auth.dto.JoinRequestDto
import demo.kotlinboilerplate.auth.auth.dto.LoginRequestDto
import demo.kotlinboilerplate.auth.auth.dto.LoginResponseDto
import demo.kotlinboilerplate.auth.token.TokenProvider
import demo.kotlinboilerplate.auth.token.mapper.TokenMapper
import demo.kotlinboilerplate.auth.token.repository.TokenRepository
import demo.kotlinboilerplate.user.user.mapper.UserMapper
import demo.kotlinboilerplate.user.user.repository.UserRepository
import demo.kotlinboilerplate.user.userrole.mapper.UserRoleMapper
import demo.kotlinboilerplate.user.userrole.repository.UserRoleRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val tokenProvider: TokenProvider,
    private val tokenRepository: TokenRepository,
    private val tokenMapper: TokenMapper,
    private val userMapper: UserMapper,
    private val userRepository: UserRepository,
    private val userRoleMapper: UserRoleMapper,
    private val userRoleRepository: UserRoleRepository,
) {
    @Transactional
    fun join(requestDto: JoinRequestDto) {
        val createdUser = userMapper.toDomain(requestDto)
        val savedUser = userRepository.save(createdUser)

        val createdRoles = userRoleMapper.toDomain(requestDto, savedUser)
        userRoleRepository.save(createdRoles)
    }

    @Transactional
    fun login(requestDto: LoginRequestDto): LoginResponseDto {
        val authenticationToken = UsernamePasswordAuthenticationToken(requestDto.email, requestDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        val userId = authentication.name.toLong()
        val createdToken =
            tokenProvider.createToken(
                userId,
                authentication.authorities.joinToString(",") { it.authority },
            )

        val refreshToken = createdToken.refreshToken

        val findToken = tokenRepository.findToken(userId)
        if (findToken != null) {
            findToken.refresh(refreshToken)
            tokenRepository.save(findToken)
        } else {
            tokenRepository.save(tokenMapper.toDomain(userId, refreshToken))
        }

        return LoginResponseDto(userId, createdToken.accessToken, createdToken.refreshToken)
    }
}
