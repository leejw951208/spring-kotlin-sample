package demo.security.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Comment("이름")
    @Column(name = "name", length = 5, nullable = false)
    val name: String,

    @Comment("나이")
    @Column(name = "age", length = 3)
    val age: Int,

    @Comment("생년월일, yyMMdd")
    @Column(name = "birth_day", length = 6)
    val birthDay: String,
)