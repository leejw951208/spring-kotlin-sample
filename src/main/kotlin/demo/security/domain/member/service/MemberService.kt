package demo.security.domain.member.service

import demo.security.domain.member.dto.SignupDto
import demo.security.domain.member.repository.MemberRepository
import demo.security.domain.member.repository.MemberRoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
    , private val memberRoleRepository: MemberRoleRepository
) {
    @Transactional
    fun signup(signupDto: SignupDto): String {
        return "success"
    }
}