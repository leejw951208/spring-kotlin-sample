package demo.springkotlinsample.auth.token

import demo.springkotlinsample.auth.token.dto.TokenResponseDto
import demo.springkotlinsample.common.exception.BaseException
import demo.springkotlinsample.common.exception.ExceptionEnum
import demo.springkotlinsample.common.security.principal.PrincipalDetails
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

@Component
class TokenProvider(
    private val tokenProperties: TokenProperties,
) {
    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenProperties.secretKey))

    fun createToken(
        id: Long,
        roles: String?,
    ): TokenResponseDto {
        val accessClaims =
            Jwts.claims()
                .add("id", id)
                .add("roles", roles)
                .add("type", "ac")
                .build()

        val refreshClaims =
            Jwts.claims()
                .add("id", id)
                .add("type", "rf")
                .build()

        val createdAccessToken =
            Jwts.builder()
                .claims(accessClaims)
                .issuer(tokenProperties.issuer)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(tokenProperties.accessExpiresIn, ChronoUnit.SECONDS)))
                .signWith(key, Jwts.SIG.HS256)
                .compact()

        val createdRefreshToken =
            Jwts.builder()
                .claims(refreshClaims)
                .issuer(tokenProperties.issuer)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(tokenProperties.refreshExpiresIn, ChronoUnit.SECONDS)))
                .signWith(key, Jwts.SIG.HS256)
                .compact()

        return TokenResponseDto(createdAccessToken, createdRefreshToken)
    }

    fun validate(token: String) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
        } catch (e: MalformedJwtException) {
            // 토큰 구조가 올바르지 않는 경우, alg, payload, signature 구조가 아닌 경우
            throw BaseException(ExceptionEnum.INVALID_TOKEN, this::class.java.name, e.stackTraceToString())
        } catch (e: ExpiredJwtException) {
            // 토큰이 만료된 경우
            throw BaseException(ExceptionEnum.EXPIRED_TOKEN, this::class.java.name, e.stackTraceToString())
        } catch (e: UnsupportedJwtException) {
            // 지원하지 않는 알고리즘을 사용한 토큰인 경우
            throw BaseException(ExceptionEnum.INVALID_TOKEN, this::class.java.name, e.stackTraceToString())
        } catch (e: IllegalArgumentException) {
            // 토큰이 null 이거나 빈 문자열인 경우
            throw BaseException(ExceptionEnum.NOT_FOUND_TOKEN, this::class.java.name, e.stackTraceToString())
        } catch (e: SignatureException) {
            // 알고리즘이 잘못된 경우
            throw BaseException(ExceptionEnum.INVALID_TOKEN, this::class.java.name, e.stackTraceToString())
        } catch (e: Exception) {
            // 그 외 예외는 500 처리
            throw BaseException(ExceptionEnum.INTERNAL_SERVER_ERROR, this::class.java.name, e.stackTraceToString())
        }
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims =
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(accessToken)
                .payload

        if (claims["type"] != "access") {
            throw BaseException(ExceptionEnum.INVALID_TOKEN, this::class.java.name, null)
        }

        if (claims["roles"] == null) {
            throw BaseException(ExceptionEnum.FORBIDDEN, this::class.java.name, null)
        }

        val authorities: Collection<GrantedAuthority> =
            claims["roles"]
                ?.toString()
                ?.split(",")
                ?.map { GrantedAuthority { it } }
                ?.toList() ?: emptyList()

        val principal = PrincipalDetails(claims["id"].toString(), "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }
}
