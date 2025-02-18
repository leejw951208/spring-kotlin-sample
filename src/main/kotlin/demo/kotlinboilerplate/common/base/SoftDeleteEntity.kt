package demo.kotlinboilerplate.common.base

import demo.kotlinboilerplate.common.util.SecurityUtil
import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@MappedSuperclass
@Where(clause = "deleted is false")
class SoftDeleteEntity: BaseEntity() {
    @Column(name = "deleted")
    var deleted: Boolean? = false
        protected set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    @Column(name = "deleted_by")
    var deletedBy: Long? = null
        protected set

    fun softDelete() {
        deleted = true
        deletedAt = LocalDateTime.now()
        deletedBy = SecurityUtil.getCurrentUserId()
    }
}