package demo.kotlinboilerplate.common.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity: BaseTimeEntity() {
    @Comment("등록자")
    @Column(name = "created_by", nullable = false)
    var createdBy: Long? = null
        protected set

    @Comment("수정자")
    @Column(name = "updated_by")
    var updatedBy: Long? = null
        protected set
}