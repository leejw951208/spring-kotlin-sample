package demo.springkotlinsample.common.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object SecurityUtil {
    fun getUserId(): Long {
        val authentication = SecurityContextHolder.getContext().authentication ?: return 0L
        if (authentication.principal == "anonymousUser") return 0L
        return (authentication.principal as UserDetails).username.toLong()
    }
}
