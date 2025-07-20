package demo.springkotlinsample.common.security.filter

import demo.springkotlinsample.auth.token.TokenProperties
import demo.springkotlinsample.auth.token.TokenProvider
import demo.springkotlinsample.common.security.SecurityUrl
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val tokenProperties: TokenProperties,
    private val tokenProvider: TokenProvider,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        // 요청 정보에서 Authorization 헤더 조회
        val header = request.getHeader(tokenProperties.header)

        // 헤더 값이 없거나 헤더 시작 문자열이 'Bearer' 가 아닌 경우 다음 필터로 넘김
        if (header == null || !header.startsWith(tokenProperties.prefix)) {
            filterChain.doFilter(request, response)
            return
        }

        // 헤더에서 토큰 추출
        val token = header.replace(tokenProperties.prefix, "").trim()

        // 토큰 검증
        // 검증 실패 시 validate 메소드에서 예외 발생
        tokenProvider.validate(token)

        // 토큰에서 인증 정보 추출하여 SecurityContextHolder 에 저장
        val authentication = tokenProvider.getAuthentication(token)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return SecurityUrl.PUBLIC_URLS.map { "/api/v1$it" }.any { request.requestURI.equals(it) }
    }
}
