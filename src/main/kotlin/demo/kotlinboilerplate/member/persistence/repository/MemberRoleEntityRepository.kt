package demo.kotlinboilerplate.member.persistence.repository

import demo.kotlinboilerplate.member.persistence.entity.MemberEntity
import demo.kotlinboilerplate.member.persistence.entity.MemberRoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRoleEntityRepository : JpaRepository<MemberRoleEntity, Long> {
    fun findByMemberId(memberId: Long?): List<MemberRoleEntity>
}