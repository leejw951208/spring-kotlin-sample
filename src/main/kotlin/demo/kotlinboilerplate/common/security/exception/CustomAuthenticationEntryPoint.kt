package demo.kotlinboilerplate.common.security.exception

import com.fasterxml.jackson.databind.ObjectMapper
import demo.kotlinboilerplate.common.exception.ExceptionEnum
import demo.kotlinboilerplate.common.exception.ExceptionResponseDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val exceptionResponse =
            ExceptionResponseDto(
                status = ExceptionEnum.UNAUTHORIZED.status,
                code = ExceptionEnum.UNAUTHORIZED.code,
                message = ExceptionEnum.UNAUTHORIZED.message,
            )

        response.status = ExceptionEnum.UNAUTHORIZED.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = Charsets.UTF_8.name()

        response.writer.write(objectMapper.writeValueAsString(exceptionResponse))
    }
}
