package demo.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String
    , val prefix: String
    , val header: String
    , val issuer: String
    , val subject: String
    , val accessTokenExpHours: Long
    , val refreshTokenExpHours: Long
)