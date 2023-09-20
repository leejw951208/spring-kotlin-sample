package demo.security.domain.member.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_member_certified")
class MemberCertified(
    isCertified: Boolean
    , member: Member
    , issueKey: String
) {
    @Id
    @Column(name = "member_id")
    var id: Long? = 0L

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member = member
        protected set

    @Comment("발급키")
    @Column(name = "issue_key")
    var issueKey = issueKey
        protected set

    @Comment("인증 여부")
    @Column(name = "is_certified")
    var isCertified = isCertified
        protected set
}