package demo.kotlinboilerplate.global.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String
    , val prefix: String
    , val header: String
    , val accessTokenExpTime: Long
    , val refreshTokenExpTime: Long
)