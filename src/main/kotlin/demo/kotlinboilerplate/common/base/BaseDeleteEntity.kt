package demo.kotlinboilerplate.common.base

import demo.kotlinboilerplate.common.security.SecurityContextUtil
import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
class BaseDeleteEntity(
    @Column(name = "deleted")
    protected var deleted: Boolean? = false,
    @Column(name = "deleted_by")
    protected var deletedBy: Long? = null,
    @Column(name = "deleted_at")
    protected var deletedAt: LocalDateTime? = null,
) : BaseEntity() {
    fun delete() {
        this.deleted = true
        this.deletedBy = SecurityContextUtil.getUserId()
        this.deletedAt = LocalDateTime.now()
    }
}

