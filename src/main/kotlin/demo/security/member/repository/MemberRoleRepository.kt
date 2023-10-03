package demo.security.member.repository

import demo.security.member.entity.Member
import demo.security.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRoleRepository : JpaRepository<MemberRole, Long> {

    fun findByMember(member: Member): List<MemberRole>
}