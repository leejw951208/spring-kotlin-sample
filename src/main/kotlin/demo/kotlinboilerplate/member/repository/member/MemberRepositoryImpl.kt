package demo.kotlinboilerplate.member.repository.member

import demo.kotlinboilerplate.member.domain.Member
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
    override fun findMember(memberId: Long): Member {
        val findMember = memberEntityRepository.findByIdOrNull(memberId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다.")
        return memberMapper.toDomain(findMember)
    }
}