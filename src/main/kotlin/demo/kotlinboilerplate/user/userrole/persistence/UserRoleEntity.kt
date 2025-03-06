package demo.kotlinboilerplate.user.userrole.persistence

import demo.kotlinboilerplate.common.base.BaseDeleteEntity
import demo.kotlinboilerplate.user.userrole.enumeration.UserRoleEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "t_user_role")
@Entity
class UserRoleEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    val role: UserRoleEnum,
) : BaseDeleteEntity()
