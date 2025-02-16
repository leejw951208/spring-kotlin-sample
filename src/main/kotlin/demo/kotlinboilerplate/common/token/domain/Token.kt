package demo.kotlinboilerplate.common.token.domain

import java.time.LocalDateTime

class Token(
    val id: Long,
    var memberId: Long,
    var refreshToken: String,
    var issuedAt: LocalDateTime
) {
}