package demo.springkotlinsample.common.security.exception

import com.fasterxml.jackson.databind.ObjectMapper
import demo.springkotlinsample.common.exception.ExceptionEnum
import demo.springkotlinsample.common.exception.ExceptionResponseDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
) : AccessDeniedHandler {
    private val logger = LoggerFactory.getLogger(CustomAccessDeniedHandler::class.java)

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: AccessDeniedException,
    ) {
        logger.error(ex.stackTraceToString())

        val exceptionResponse =
            ExceptionResponseDto(
                status = ExceptionEnum.FORBIDDEN.status,
                code = ExceptionEnum.FORBIDDEN.code,
            )

        response.status = ExceptionEnum.FORBIDDEN.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = Charsets.UTF_8.name()

        response.writer.write(objectMapper.writeValueAsString(exceptionResponse))
    }
}
