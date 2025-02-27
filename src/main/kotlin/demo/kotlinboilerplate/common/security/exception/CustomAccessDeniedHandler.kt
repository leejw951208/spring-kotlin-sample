package demo.kotlinboilerplate.common.security.exception

import com.fasterxml.jackson.databind.ObjectMapper
import demo.kotlinboilerplate.common.exception.ExceptionEnum
import demo.kotlinboilerplate.common.exception.ExceptionResponseDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        val exceptionResponse =
            ExceptionResponseDto(
                status = ExceptionEnum.FORBIDDEN.status,
                code = ExceptionEnum.FORBIDDEN.code,
                message = ExceptionEnum.FORBIDDEN.message,
            )

        response.status = ExceptionEnum.FORBIDDEN.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = Charsets.UTF_8.name()

        response.writer.write(objectMapper.writeValueAsString(exceptionResponse))
    }
}
