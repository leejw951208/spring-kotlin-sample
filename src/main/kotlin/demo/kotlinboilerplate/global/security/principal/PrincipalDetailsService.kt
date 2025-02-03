package demo.kotlinboilerplate.global.security.principal

import demo.kotlinboilerplate.member.persistence.repository.MemberRepository
import demo.kotlinboilerplate.member.persistence.repository.MemberRoleRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PrincipalDetailsService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val findMember = memberRepository.findByEmail(username) ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "사용자 정보가 없습니다."
        )
        val findMemberRoles = memberRoleRepository.findByMember(findMember)
        val authorities: Collection<GrantedAuthority> = findMemberRoles.map { GrantedAuthority { it.role.name }}
        return PrincipalDetails(findMember.id.toString(), findMember.password, authorities)
    }
}