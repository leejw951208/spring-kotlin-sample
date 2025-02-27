package demo.kotlinboilerplate.common.base

import demo.kotlinboilerplate.common.security.SecurityUtil
import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
class BaseDeleteEntity(
    @Column(name = "deleted")
    val deleted: Boolean? = false,
    @Column(name = "deleted_by")
    val deletedBy: Long? = null,
    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null,
) : BaseEntity() {
    companion object {
        fun softDelete(): BaseDeleteEntity {
            return BaseDeleteEntity(
                deleted = true,
                deletedBy = SecurityUtil.getUserId(),
                deletedAt = LocalDateTime.now(),
            )
        }
    }
}
