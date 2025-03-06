package demo.kotlinboilerplate.common.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object SecurityContextUtil {
    fun getUserId(): Long {
        val authentication = SecurityContextHolder.getContext().authentication ?: return 0L
        if (authentication.principal == "anonymousUser") return 0L
        return (authentication.principal as UserDetails).username.toLong()
    }
}
