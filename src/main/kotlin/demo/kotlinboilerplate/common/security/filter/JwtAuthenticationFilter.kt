package demo.kotlinboilerplate.common.security.filter

import demo.kotlinboilerplate.common.token.TokenProperties
import demo.kotlinboilerplate.common.token.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthenticationFilter(
    private val jwtProperties: TokenProperties,
    private val jwtProvider: TokenProvider
): OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizeHeader = request.getHeader(jwtProperties.header)
        if (authorizeHeader == null || !authorizeHeader.startsWith(jwtProperties.prefix)) {
            filterChain.doFilter(request, response)
            return;
        }
        val token = request.getHeader(jwtProperties.header).replace(jwtProperties.prefix, "")
        jwtProvider.verify(token)

        val authentication = jwtProvider.getAuthentication(token)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }
}