package demo.kotlinboilerplate.user.userrole.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRoleEntityRepository : JpaRepository<UserRoleEntity, Long> {
    fun findAllByUserId(userId: Long?): List<UserRoleEntity>
}
