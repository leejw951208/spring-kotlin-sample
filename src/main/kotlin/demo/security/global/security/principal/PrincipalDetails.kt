package demo.security.global.security.principal

import demo.security.member.entity.Member
import demo.security.member.entity.MemberRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import kotlin.collections.List

class PrincipalDetails(
    private val username: String?,
    private val password: String?,
    private val authorities: Collection<GrantedAuthority>?
): UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return authorities
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}