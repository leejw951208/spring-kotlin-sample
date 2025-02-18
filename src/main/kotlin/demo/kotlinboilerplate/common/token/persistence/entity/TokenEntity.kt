package demo.kotlinboilerplate.common.token.persistence.entity

import demo.kotlinboilerplate.common.base.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_token")
class TokenEntity(id: Long?, memberId: Long, refreshToken: String, issuedAt: LocalDateTime) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = id
        protected set

    @Column(name = "member_id", nullable = false)
    var memberId = memberId
        protected set

    @Column(name = "refresh_token", nullable = false)
    var refreshToken = refreshToken
        protected set

    @Column(name = "issued_at", nullable = false)
    var issuedAt = issuedAt
        protected set
}