package demo.kotlinboilerplate.common.token.domain

import java.time.LocalDateTime

class TokenSave(
    var memberId: Long,
    var refreshToken: String,
    var issuedAt: LocalDateTime,
) {
}