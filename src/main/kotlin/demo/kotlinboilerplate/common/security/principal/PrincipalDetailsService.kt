package demo.kotlinboilerplate.common.security.principal

import demo.kotlinboilerplate.common.exception.BaseException
import demo.kotlinboilerplate.common.exception.ExceptionEnum
import demo.kotlinboilerplate.user.user.repository.UserRepository
import demo.kotlinboilerplate.user.userrole.repository.UserRoleRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class PrincipalDetailsService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val findUser =
            userRepository.findOne(email)
                ?: throw BaseException(ExceptionEnum.NOT_FOUND_USER, this::class.java.name, null)

        val findRoleList = userRoleRepository.findUserRoles(findUser.id!!)
        if (findRoleList.isEmpty()) {
            throw BaseException(ExceptionEnum.FORBIDDEN, this::class.java.name, null)
        }

        val authorities: Collection<GrantedAuthority> = findRoleList.map { GrantedAuthority { it.role.name } }
        return PrincipalDetails(findUser.id.toString(), findUser.password, authorities)
    }
}
