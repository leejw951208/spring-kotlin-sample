package demo.security.global.filter

import demo.security.global.jwt.JwtProperties
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthenticationFilter(
    private val jwtProperties: JwtProperties
): OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizeHeader = request.getHeader(jwtProperties.header)
        if (authorizeHeader == null || !authorizeHeader.startsWith(jwtProperties.prefix)) {
            filterChain.doFilter(request, response)
        }

        val token = request.getHeader(jwtProperties.header).replace(jwtProperties.prefix, "")


        filterChain.doFilter(request, response)
    }
}