package demo.kotlinboilerplate.member.persistence.entity

import demo.kotlinboilerplate.common.entity.BaseTimeEntity
import demo.kotlinboilerplate.member.enumeration.RoleType
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_member_role")
class MemberRoleEntity(
    member: MemberEntity
    , role: RoleType
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

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
