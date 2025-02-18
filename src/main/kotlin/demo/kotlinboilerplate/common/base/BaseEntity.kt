package demo.kotlinboilerplate.common.base

import demo.kotlinboilerplate.common.util.SecurityUtil
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity {
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    var createdBy: Long = SecurityUtil.getCurrentUserId()
        protected set

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedBy
    @Column(name = "updated_by")
    var updatedBy: Long? = null
        protected set

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
        protected set
}