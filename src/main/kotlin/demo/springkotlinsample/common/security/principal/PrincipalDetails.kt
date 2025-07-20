package demo.springkotlinsample.common.security.principal

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PrincipalDetails(
    private val username: String?,
    private val password: String?,
    private val authorities: Collection<GrantedAuthority>,
) : UserDetails {
    override fun getUsername(): String? {
        return username
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }
}
