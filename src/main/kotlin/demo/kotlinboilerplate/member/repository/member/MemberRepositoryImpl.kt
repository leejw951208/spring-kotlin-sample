package demo.kotlinboilerplate.member.repository.member

import demo.kotlinboilerplate.member.domain.Member
import demo.kotlinboilerplate.member.domain.MemberSave
import demo.kotlinboilerplate.member.mapper.MemberMapper
import demo.kotlinboilerplate.member.persistence.repository.MemberEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
class MemberRepositoryImpl(
    private val memberEntityRepository: MemberEntityRepository,
    private val memberMapper: MemberMapper
): MemberRepository {
    override fun findMember(memberId: Long): Member? {
        val findMember = memberEntityRepository.findByIdOrNull(memberId) ?: return null
        return memberMapper.toDomain(findMember)
    }

    override fun existsMember(email: String): Boolean {
        return memberEntityRepository.existsByEmail(email)
    }

    override fun save(member: MemberSave) {
        memberEntityRepository.save(memberMapper.toEntity(member))
    }

    override fun delete(memberId: Long) {
        val findMember = memberEntityRepository.findByIdOrNull(memberId) ?: return
        findMember.softDelete()
    }
}