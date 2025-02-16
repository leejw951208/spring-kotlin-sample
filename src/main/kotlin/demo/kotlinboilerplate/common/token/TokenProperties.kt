package demo.kotlinboilerplate.common.token

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class TokenProperties(
    val secretKey: String
    , val prefix: String
    , val header: String
    , val accessTokenExpTime: Long
    , val refreshTokenExpTime: Long
)