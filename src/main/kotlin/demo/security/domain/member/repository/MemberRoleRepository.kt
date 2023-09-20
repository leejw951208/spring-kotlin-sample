package demo.security.domain.member.repository

import demo.security.domain.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRoleRepository : JpaRepository<MemberRole, Long> {
}