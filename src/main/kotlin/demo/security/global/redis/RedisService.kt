package demo.security.global.redis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(
    private val stringRedisTemplate: StringRedisTemplate
) {

    fun getValue(key: String?): String? {
        val values = stringRedisTemplate.opsForValue()
        return values[key!!]
    }

    fun setValue(key: String?, value: String?) {
        val values = stringRedisTemplate.opsForValue()
        values[key!!] = value!!

        stringRedisTemplate.expire(key, Duration.ofSeconds(180))
    }
}