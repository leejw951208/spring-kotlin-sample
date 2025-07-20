package demo.springkotlinsample.common.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler
    fun handleResponseStatusException(
        ex: BaseException,
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        logger.error(ex.stackTraceToString())

        val exceptionEnum = ex.exceptionEnum
        val exceptionResponse =
            ExceptionResponseDto(
                status = exceptionEnum.status,
                code = exceptionEnum.code,
            )

        return ResponseEntity.status(exceptionEnum.status).body(exceptionResponse)
    }

    @ExceptionHandler
    fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        logger.error(ex.stackTraceToString())

        val exceptionResponse =
            when (ex.cause) {
                is InvalidFormatException ->
                    ExceptionResponseDto(
                        status = ExceptionEnum.INVALID_PARAMETER.status,
                        code = ExceptionEnum.INVALID_PARAMETER.code,
                    )

                is MismatchedInputException ->
                    ExceptionResponseDto(
                        status = ExceptionEnum.MISSING_PARAMETER.status,
                        code = ExceptionEnum.MISSING_PARAMETER.code,
                    )

                else ->
                    ExceptionResponseDto(
                        status = ExceptionEnum.BAD_REQUEST.status,
                        code = ExceptionEnum.BAD_REQUEST.code,
                    )
            }

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }

    @ExceptionHandler
    fun handleInternalAuthenticationServiceException(
        ex: InternalAuthenticationServiceException,
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        logger.error(ex.stackTraceToString())

        val exceptionResponse =
            ExceptionResponseDto(
                status = ExceptionEnum.NOT_FOUND_USER.status,
                code = ExceptionEnum.NOT_FOUND_USER.code,
            )

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }

    @ExceptionHandler
    fun handleAuthenticationException(
        ex: AuthenticationException,
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        logger.error(ex.stackTraceToString())

        val exceptionResponse =
            ExceptionResponseDto(
                status = ExceptionEnum.NOT_FOUND_USER.status,
                code = ExceptionEnum.NOT_FOUND_USER.code,
            )

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }

    @ExceptionHandler
    fun handleException(
        ex: Exception,
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        logger.error(ex.stackTraceToString())

        val exceptionResponse =
            ExceptionResponseDto(
                status = ExceptionEnum.INTERNAL_SERVER_ERROR.status,
                code = ExceptionEnum.INTERNAL_SERVER_ERROR.code,
            )

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }
}
