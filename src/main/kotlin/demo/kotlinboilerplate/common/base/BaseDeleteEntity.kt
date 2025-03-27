package demo.kotlinboilerplate.common.base

import demo.kotlinboilerplate.common.security.SecurityUtil
import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Filter
import org.hibernate.annotations.FilterDef
import org.hibernate.annotations.ParamDef
import java.time.LocalDateTime

@FilterDef(name = "softDeleteFilter", parameters = [ParamDef(name = "deleted", type = Boolean::class)])
@Filter(name = "softDeleteFilter", condition = "deleted = false")
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
        this.deletedBy = SecurityUtil.getUserId()
        this.deletedAt = LocalDateTime.now()
    }
}
