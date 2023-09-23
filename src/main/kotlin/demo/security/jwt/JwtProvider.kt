package demo.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties
) {

    fun createAccessToken(email: String): String {
        return Jwts.builder()
            .setIssuer(jwtProperties.issuer)
            .setSubject(jwtProperties.subject)
            .claim("email", email)
            .signWith(SecretKeySpec(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.jcaName))
            .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
            .setExpiration(Date.from(Instant.now().plus(jwtProperties.accessTokenExpHours, ChronoUnit.HOURS)))
            .compact()
    }

    fun createRefreshToken(email: String): String {
        return Jwts.builder()
            .setIssuer(jwtProperties.issuer)
            .setSubject(jwtProperties.subject)
            .claim("email", email)
            .signWith(SecretKeySpec(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.jcaName))
            .setExpiration(Date.from(Instant.now().plus(jwtProperties.refreshTokenExpHours, ChronoUnit.HOURS)))
            .compact()
    }
}