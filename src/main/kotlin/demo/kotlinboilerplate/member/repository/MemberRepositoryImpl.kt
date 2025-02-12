package demo.kotlinboilerplate.member.repository

import demo.kotlinboilerplate.global.exception.ResponseStatusExceptionDto
import demo.kotlinboilerplate.member.domain.Member
import demo.kotlinboilerplate.member.mapper.MemberMapper
import demo.kotlinboilerplate.member.persistence.repository.MemberEntityRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
class MemberRepositoryImpl(
    private val memberEntityRepository: MemberEntityRepository,
    private val memberMapper: MemberMapper
): MemberRepository {
    override fun findById(memberId: Long): Member {
        val findMember = memberEntityRepository.findById(memberId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 정보가 없습니다.")
        }
        return memberMapper.toDomain(findMember)
    }
}