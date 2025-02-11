package demo.kotlinboilerplate.member.persistence.repository

import demo.kotlinboilerplate.member.persistence.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberEntityRepository : JpaRepository<MemberEntity, Long> {
    fun findByEmail(email: String?): MemberEntity?
    fun existsByEmail(email: String?): Boolean
}