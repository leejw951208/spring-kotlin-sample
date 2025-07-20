package demo.springkotlinsample.common.swagger

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@Repeatable
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponses(value = [ApiResponse()])
annotation class SwaggerApiResponse(
    val responseCode: String,
    val description: String,
)
