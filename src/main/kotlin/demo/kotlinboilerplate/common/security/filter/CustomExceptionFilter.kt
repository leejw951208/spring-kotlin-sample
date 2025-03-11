package demo.kotlinboilerplate.common.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import demo.kotlinboilerplate.common.exception.BaseException
import demo.kotlinboilerplate.common.exception.ExceptionEnum
import demo.kotlinboilerplate.common.exception.ExceptionResponseDto
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.server.ResponseStatusException
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.jvm.Throws

class CustomExceptionFilter(
    private val objectMapper: ObjectMapper,
) : OncePerRequestFilter() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: BaseException) {
            setErrorResponse(request, response, ex)
        }
    }

    private fun setErrorResponse(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: BaseException,
    ) {
        val exception = ex.exceptionEnum
        response.status = exception.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        response.writer.write(objectMapper.writeValueAsString(ExceptionResponseDto(
            status = exception.status,
            code = exception.code,
        )))

        return
    }
}
