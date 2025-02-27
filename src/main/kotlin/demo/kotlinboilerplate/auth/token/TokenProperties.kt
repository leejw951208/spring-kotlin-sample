package demo.kotlinboilerplate.auth.token

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class TokenProperties(
    val secretKey: String,
    val prefix: String,
    val header: String,
    val issuer: String,
    val accessTokenExpSecond: Long,
    val refreshTokenExpSecond: Long,
)
