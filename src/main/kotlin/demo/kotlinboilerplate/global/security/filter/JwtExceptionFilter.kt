package demo.kotlinboilerplate.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
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

class JwtExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal( request: HttpServletRequest,  response: HttpServletResponse, filterChain: FilterChain) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: ResponseStatusException) {
            setErrorResponse(request, response, ex)
        }
    }

    private fun setErrorResponse(request: HttpServletRequest, response: HttpServletResponse, ex: ResponseStatusException) {
        response.status = ex.statusCode.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val map = HashMap<String, Any>()
        map["error"] = HttpStatus.valueOf(ex.statusCode.value())
        map["path"] = request.requestURL
        map["message"] = ex.reason.toString()
        map["status"] = ex.statusCode.value()
        map["timestamp"] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        response.writer.write(objectMapper.writeValueAsString(map))
    }
}