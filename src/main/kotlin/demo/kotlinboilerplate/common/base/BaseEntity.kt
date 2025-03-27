package demo.kotlinboilerplate.common.base

import demo.kotlinboilerplate.common.security.SecurityUtil
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
class BaseEntity(
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    protected var createdBy: Long? = null,
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected var createdAt: LocalDateTime? = null,
    @LastModifiedBy
    @Column(name = "updated_by", insertable = false)
    protected var updatedBy: Long? = null,
    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    protected var updatedAt: LocalDateTime? = null,
)
