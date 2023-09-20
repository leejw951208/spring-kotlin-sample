package demo.security.domain.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_member_certified")
class MemberCertified(
    isCertified: Boolean
    , member: Member
    , issuedKey: String
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
    @Column(name = "issued_key")
    var issuedKey = issuedKey
        protected set

    @Comment("인증 여부")
    @Column(name = "is_certified")
    var isCertified = isCertified
        protected set
}