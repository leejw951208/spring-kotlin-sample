package demo.kotlinboilerplate.member.persistence.entity

import demo.kotlinboilerplate.global.util.PasswordConverter
import demo.kotlinboilerplate.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime

@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_member")
class MemberEntity (
    email: String?
    , password: String?
    , name: String?
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
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

    @Comment("나이")
    @Column(name = "age", length = 3)
    var age: Int? = null
        protected set

    @Comment("생년월일")
    @Column(name = "birth_day", length = 6)
    var birthDay: LocalDate? = null
        protected set

    @Comment("승인여부")
    @Column(name = "is_approved", nullable = false, columnDefinition = "tinyint(1)")
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
