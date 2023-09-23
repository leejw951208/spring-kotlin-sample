package demo.security.redis

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisController(
    private val redisService: RedisService
) {

    @PostMapping("/certified/{key}")
    fun setCertified(@PathVariable key: String): String? {
        redisService.setValue(key, "12345")
        return "saved"
    }

    @GetMapping("/certified/{key}")
    fun getCertified(@PathVariable key: String): String? {
        return redisService.getValue(key)
    }
}