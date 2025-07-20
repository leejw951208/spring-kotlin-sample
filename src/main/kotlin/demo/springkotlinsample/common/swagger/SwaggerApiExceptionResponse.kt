package demo.springkotlinsample.common.swagger

import demo.springkotlinsample.common.exception.ExceptionEnum

annotation class SwaggerApiExceptionResponse(
    val exceptions: Array<ExceptionEnum>,
)
