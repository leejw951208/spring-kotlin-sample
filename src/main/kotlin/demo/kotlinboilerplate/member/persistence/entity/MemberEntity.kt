package demo.kotlinboilerplate.member.persistence.entity

import demo.kotlinboilerplate.common.util.PasswordConverter
import demo.kotlinboilerplate.common.base.SoftDeleteEntity
import demo.kotlinboilerplate.member.domain.Member
import demo.kotlinboilerplate.member.domain.MemberSave
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_member")
class MemberEntity (
    id: Long?
    , email: String
    , password: String
    , name: String
) : SoftDeleteEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = id
        protected set

    @Comment("이메일")
    @Column(name = "email", length = 100, nullable = false, unique = true)
    var email = email
        protected set

    @Comment("비밀번호")
    @Convert(converter = PasswordConverter::class)
    @Column(name = "password", nullable = false)
    var password = password
        protected set

    @Comment("이름")
    @Column(name = "name", length = 5, nullable = false)
    var name = name
        protected set

    @Comment("승인여부")
    @Column(name = "is_approved", nullable = false)
    var isApproved: Boolean = false
        protected set

    @Comment("승인일자")
    @Column(name = "approved_at")
    var approvedAt: LocalDateTime? = null
        protected set

    fun isApproved(isApproved: Boolean) {
        this.isApproved = isApproved
    }
}
