package demo.security.domain.entity

import demo.security.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_member")
class Member (
    account: String
    , password: String
    , name: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Comment("아이디")
    @Column(name = "account", length = 30, nullable = false)
    var account = account
        protected set

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

    @Comment("생년월일, yyMMdd")
    @Column(name = "birth_day", length = 6)
    var birthDay: String? = null
        protected set
}
