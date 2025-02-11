package demo.kotlinboilerplate.global.security.principal

import demo.kotlinboilerplate.member.persistence.repository.MemberEntityRepository
import demo.kotlinboilerplate.member.persistence.repository.MemberRoleEntityRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PrincipalDetailsService(
    private val memberRepository: MemberEntityRepository,
    private val memberRoleRepository: MemberRoleEntityRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val findMember = memberRepository.findByEmail(username) ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "사용자 정보가 없습니다."
        )
        if (!findMember.isApproved) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "가입 승인이 완료되지 않았습니다.")
        val findMemberRoles = memberRoleRepository.findByMember(findMember)
        val authorities: Collection<GrantedAuthority> = findMemberRoles.map { GrantedAuthority { it.role.name }}
        return PrincipalDetails(findMember.id.toString(), findMember.password, authorities)
    }
}