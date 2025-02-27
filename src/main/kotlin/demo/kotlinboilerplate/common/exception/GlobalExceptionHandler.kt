package demo.kotlinboilerplate.common.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun handleResponseStatusException(
        ex: BaseException,
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        val exceptionEnum = ex.exceptionEnum

        val exceptionResponse =
            ExceptionResponseDto(
                status = exceptionEnum.status,
                code = exceptionEnum.code,
                message = exceptionEnum.message,
            )

        return ResponseEntity.status(exceptionEnum.status).body(exceptionResponse)
    }

    @ExceptionHandler
    fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        val exceptionResponse =
            when (ex.cause) {
                is InvalidFormatException ->
                    ExceptionResponseDto(
                        status = ExceptionEnum.INVALID_PARAMETER.status,
                        code = ExceptionEnum.INVALID_PARAMETER.code,
                        message = ExceptionEnum.INVALID_PARAMETER.message,
                    )

                is MismatchedInputException ->
                    ExceptionResponseDto(
                        status = ExceptionEnum.MISSING_PARAMETER.status,
                        code = ExceptionEnum.MISSING_PARAMETER.code,
                        message = ExceptionEnum.MISSING_PARAMETER.message,
                    )

                else ->
                    ExceptionResponseDto(
                        status = ExceptionEnum.BAD_REQUEST.status,
                        code = ExceptionEnum.BAD_REQUEST.code,
                        message = ExceptionEnum.BAD_REQUEST.message,
                    )
            }

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }

    @ExceptionHandler
    fun handleInternalAuthenticationServiceException(
        ex: InternalAuthenticationServiceException,
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        val exceptionResponse =
            ExceptionResponseDto(
                status = ExceptionEnum.FORBIDDEN.status,
                code = ExceptionEnum.FORBIDDEN.code,
                message = ExceptionEnum.FORBIDDEN.message,
            )

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }

    @ExceptionHandler
    fun handleException(
        ex: Exception,
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        val exceptionResponse =
            ExceptionResponseDto(
                status = ExceptionEnum.INTERNAL_SERVER_ERROR.status,
                code = ExceptionEnum.INTERNAL_SERVER_ERROR.code,
                message = ExceptionEnum.INTERNAL_SERVER_ERROR.message,
            )

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }
}
