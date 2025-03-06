package demo.kotlinboilerplate.auth.token.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "t_token")
@Entity
class TokenEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "user_id", nullable = false, unique = true)
    val userId: Long,
    @Column(name = "refresh_token", nullable = false)
    val refreshToken: String,
)
