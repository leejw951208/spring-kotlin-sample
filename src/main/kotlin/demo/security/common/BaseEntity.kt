package demo.security.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity: BaseTimeEntity() {
    @Comment("등록자 아이디")
    @Column(name = "reg_id")
    var regId: String? = null
        protected set

    @Comment("수정자 아이디")
    @Column(name = "mod_id")
    var modId: String? = null
        protected set
}