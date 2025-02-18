package demo.kotlinboilerplate.common.util

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object SecurityUtil {
    fun getCurrentUserId(): Long {
        val authentication = SecurityContextHolder.getContext().authentication ?: return 0L
        return (authentication.principal as UserDetails).username.toLong()
    }
}