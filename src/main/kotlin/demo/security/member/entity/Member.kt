package demo.security.member.entity

import demo.security.global.util.PasswordConverter
import demo.security.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_member")
class Member (
    email: String?
    , password: String?
    , name: String?
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
        protected set

    @Comment("이메일")
    @Column(name = "email", length = 100, nullable = false, unique = true)
    var email = email
        protected set

    @Convert(converter = PasswordConverter::class)
    @Comment("비밀번호")
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

    @Comment("인증상태")
    @Column(name = "is_verified", nullable = false, columnDefinition = "tinyint(1)")
    var isVerified: Boolean = false
        protected set

    fun isVerified(isVerified: Boolean) {
        this.isVerified = isVerified
    }
}
