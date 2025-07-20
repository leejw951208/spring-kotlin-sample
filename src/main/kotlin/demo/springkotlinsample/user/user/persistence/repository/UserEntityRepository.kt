package demo.springkotlinsample.user.user.persistence.repository

import demo.springkotlinsample.user.user.persistence.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserEntityRepository : JpaRepository<UserEntity, Long>, UserEntityCustomRepository {
    @Query("select u from UserEntity u where u.id = :id")
    fun findByIdOrNull(
        @Param("id") id: Long,
    ): UserEntity?

    fun findByEmail(loginId: String): UserEntity?

    fun findByEmailAndPassword(
        loginId: String,
        password: String,
    ): UserEntity?

}
