package demo.security.global.redis

import demo.security.global.jwt.JwtProperties
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(
    private val stringRedisTemplate: StringRedisTemplate,
    private val jwtProperties: JwtProperties
) {

    fun getAuthNumber(key: String?): String? {
        return getValue(key)
    }

    fun setAuthNumber(key: String?, value: String?) {
        val values = stringRedisTemplate.opsForValue()
        values[key!!] = value!!

        stringRedisTemplate.expire(key, Duration.ofHours(jwtProperties.accessTokenExpTime))
    }

    fun getRefreshToken(key: String?): String? {
        return getValue(key)
    }

    fun setRefreshToken(key: String?, value: String?) {
        val values = stringRedisTemplate.opsForValue()
        values[key!!] = value!!

        stringRedisTemplate.expire(key, Duration.ofHours(jwtProperties.refreshTokenExpTime))
    }

    private fun getValue(key: String?): String? {
        val values = stringRedisTemplate.opsForValue()
        return values[key!!]
    }
}