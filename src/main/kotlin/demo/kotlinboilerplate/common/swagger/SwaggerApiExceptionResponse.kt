package demo.kotlinboilerplate.common.swagger

import demo.kotlinboilerplate.common.exception.ExceptionEnum

annotation class SwaggerApiExceptionResponse(
    val exceptions: Array<ExceptionEnum>
)
