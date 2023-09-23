package demo.security.member.entity

import demo.security.member.RoleType
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_member_role")
class MemberRole(
    member: Member
    , role: RoleType
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member = member
        protected set

    @Enumerated(EnumType.STRING)
    @Comment("권한")
    @Column(name = "role", nullable = false)
    var role = role
        protected set
}
