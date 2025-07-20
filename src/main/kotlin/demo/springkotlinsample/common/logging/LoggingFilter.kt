package demo.springkotlinsample.common.logging

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.io.InputStream
import java.util.UUID

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class LoggingFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val uuid = UUID.randomUUID()
            MDC.put("request_id", uuid.toString())
            if (isAsyncDispatch(request)) {
                filterChain.doFilter(request, response)
            } else {
                doFilterWrapped(RequestWrapper(request), ResponseWrapper(response), filterChain)
            }
        } finally {
            MDC.clear()
        }
    }

    @Throws(ServletException::class, IOException::class)
    fun doFilterWrapped(
        request: RequestWrapper,
        response: ResponseWrapper,
        filterChain: FilterChain,
    ) {
        try {
            logRequest(request)
            filterChain.doFilter(request, response)
        } finally {
            logResponse(response)
            response.copyBodyToResponse()
        }
    }

    @Throws(IOException::class)
    fun logRequest(request: RequestWrapper) {
        val queryString = request.queryString
        val uri: String? =
            if (queryString == null) {
                request.requestURI
            } else {
                request.requestURI + queryString
            }
        logger.info("Request: ${request.method}, uri: $uri, contentType: ${request.contentType}")

        logPayload("Request", request.contentType, request.inputStream)
    }

    @Throws(IOException::class)
    fun logResponse(response: ResponseWrapper) {
        logPayload("Response", response.contentType, response.contentInputStream)
    }

    @Throws(IOException::class)
    fun logPayload(
        prefix: String,
        contentType: String?,
        inputStream: InputStream,
    ) {
        val mediaType = MediaType.valueOf(contentType ?: "application/json")
        val visible = isVisible(mediaType)
        if (visible) {
            val content = StreamUtils.copyToByteArray(inputStream)
            if (content.isNotEmpty()) {
                val contentString = String(content)
                logger.info("$prefix Payload: $contentString")
            }
        } else {
            logger.info("$prefix Payload: Binary Content")
        }
    }

    fun isVisible(mediaType: MediaType): Boolean {
        val visibleTypes =
            listOf(
                MediaType.valueOf("text/*"),
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_XML,
                MediaType.valueOf("application/*+json"),
                MediaType.valueOf("application/*+xml"),
                MediaType.MULTIPART_FORM_DATA,
                MediaType.TEXT_PLAIN,
            )
        return visibleTypes.any { it.includes(mediaType) }
    }
}
