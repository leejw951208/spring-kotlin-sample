package demo.kotlinboilerplate.common.token.domain

import java.time.LocalDateTime

class Token(
    val id: Long? = null,
    val memberId: Long,
    var refreshToken: String,
    var issuedAt: LocalDateTime
) {
    companion object {
        fun from(memberId: Long, refreshToken: String): Token {
            return Token(null, memberId, refreshToken, LocalDateTime.now())
        }
    }

    fun refresh(refreshToken: String) {
        this.refreshToken = refreshToken
        this.issuedAt = LocalDateTime.now()
    }
}