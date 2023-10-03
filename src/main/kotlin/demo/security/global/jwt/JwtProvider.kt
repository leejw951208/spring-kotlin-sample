package demo.security.global.jwt

import demo.security.global.security.principal.PrincipalDetails
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.lang.IllegalArgumentException
import java.nio.charset.StandardCharsets
import java.security.Key
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.ArrayList
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties
) {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    fun generateToken(authentication: Authentication): JwtTokenDto {
        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authentication.authorities.joinToString(","))
            .signWith(
                SecretKeySpec(
                    jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.jcaName
                )
            )
            .setExpiration(Date.from(Instant.now().plus(jwtProperties.accessTokenExpHours, ChronoUnit.HOURS)))
            .compact()

        val refreshToken = Jwts.builder()
            .signWith(
                SecretKeySpec(
                    jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.jcaName
                )
            )
            .setExpiration(Date.from(Instant.now().plus(jwtProperties.refreshTokenExpHours, ChronoUnit.HOURS)))
            .compact()

        return JwtTokenDto(accessToken, refreshToken)
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
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
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