package demo.kotlinboilerplate.member.service

import demo.kotlinboilerplate.member.persistence.repository.MemberEntityRepository
import demo.kotlinboilerplate.member.persistence.repository.MemberRoleEntityRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberEntityRepository,
    private val memberRoleRepository: MemberRoleEntityRepository
) {
    @Transactional(readOnly = true)
    fun findMember() {

    }
}