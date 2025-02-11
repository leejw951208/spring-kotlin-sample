package demo.kotlinboilerplate.auth.persistence.entity

import demo.kotlinboilerplate.member.persistence.entity.MemberEntity
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_token")
class TokenEntity(memberId: Long, refreshToken: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "member_id", nullable = false)
    var memberId = memberId
        protected set

    @Column(name = "refresh_token", nullable = false)
    var refreshToken = refreshToken
        protected set
}