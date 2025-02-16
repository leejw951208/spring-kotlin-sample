package demo.kotlinboilerplate.member.repository.memberrole

import demo.kotlinboilerplate.member.domain.MemberRole
import demo.kotlinboilerplate.member.mapper.MemberRoleMapper
import demo.kotlinboilerplate.member.persistence.entity.MemberRoleEntity
import demo.kotlinboilerplate.member.persistence.repository.MemberRoleEntityRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
class MemberRoleRepositoryImpl(
    private val memberRoleMapper: MemberRoleMapper,
    private val memberRoleEntityRepository: MemberRoleEntityRepository
): MemberRoleRepository {
    override fun findMemberRoles(memberId: Long): List<MemberRole> {
        val findMemberRoles = memberRoleEntityRepository.findByMemberId(memberId)
        return memberRoleMapper.toDomain(findMemberRoles)
    }
}