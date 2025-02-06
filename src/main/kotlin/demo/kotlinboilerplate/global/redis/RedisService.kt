package demo.kotlinboilerplate.global.redis

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

    fun setValue(key: String, value: String, duration: Duration? = null) {
        if (duration != null) {
            stringRedisTemplate.opsForValue().set(key, value, duration)
        } else {
            stringRedisTemplate.opsForValue().set(key, value)
        }
    }
}