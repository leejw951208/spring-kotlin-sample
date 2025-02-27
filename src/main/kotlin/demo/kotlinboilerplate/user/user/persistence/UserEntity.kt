package demo.kotlinboilerplate.user.user.persistence

import demo.kotlinboilerplate.common.base.BaseDeleteEntity
import demo.kotlinboilerplate.common.converter.PasswordConverter
import demo.kotlinboilerplate.user.user.enumeration.UserStatusEnum
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "user")
@Entity
class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "email", nullable = false, length = 50, unique = true)
    val email: String,
    @Convert(converter = PasswordConverter::class)
    @Column(name = "password", nullable = false)
    val password: String,
    @Column(name = "name", nullable = false, length = 10)
    val name: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    val status: UserStatusEnum,
) : BaseDeleteEntity()
