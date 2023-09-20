package demo.security.domain.service

import demo.security.domain.dto.SignupDto
import demo.security.domain.entity.Member
import demo.security.domain.repository.MemberRepository
import demo.security.domain.repository.MemberRoleRepository
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