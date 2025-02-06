package demo.kotlinboilerplate.global.jwt

import demo.kotlinboilerplate.global.security.principal.PrincipalDetails
import demo.kotlinboilerplate.auth.dto.LoginResponseDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.lang.IllegalArgumentException
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties
) {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    fun generateToken(email: String, authentication: Authentication): LoginResponseDto {
        val memberId = authentication.name
        val roles = authentication.authorities.joinToString(",") { it.authority }

        val accessToken = Jwts.builder()
            .setSubject(memberId)
            .claim("auth", roles)
            .signWith(
                SecretKeySpec(
                    jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.jcaName
                )
            )
            .setExpiration(Date.from(Instant.now().plus(jwtProperties.accessTokenExpTime, ChronoUnit.HOURS)))
            .compact()

        val refreshToken = Jwts.builder()
            .signWith(
                SecretKeySpec(
                    jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.jcaName
                )
            )
            .setExpiration(Date.from(Instant.now().plus(jwtProperties.refreshTokenExpTime, ChronoUnit.HOURS)))
            .compact()

        return LoginResponseDto(memberId.toLong(), email, accessToken, refreshToken)
    }

    fun verify(token: String?) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SecretKeySpec(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.jcaName))
                .build()
                .parseClaimsJws(token)
        } catch (e: MalformedJwtException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        } catch (e: ExpiredJwtException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
        } catch (e: UnsupportedJwtException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)
        if (claims["auth"] == null) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "권한 정보가 없습니다.")
        }

        val authorities: Collection<GrantedAuthority> = claims["auth"]
            ?.toString()
            ?.split(",")
            ?.map { GrantedAuthority { it } }
            ?.toList() ?: emptyList()

        val principal = PrincipalDetails(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(SecretKeySpec(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.jcaName))
                .build()
                .parseClaimsJws(accessToken)
                .body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}