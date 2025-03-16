package demo.kotlinboilerplate.common.swagger

import demo.kotlinboilerplate.common.exception.ExceptionEnum
import demo.kotlinboilerplate.common.exception.ExceptionResponseDto
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openApi(): OpenAPI {
        val securityScheme =
            SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .`in`(SecurityScheme.In.HEADER)
                .name("Authorization")
                .scheme("bearer")
                .bearerFormat("JWT")

        val securityRequirement = SecurityRequirement().addList("Bearer Token")

        return OpenAPI()
            .info(Info().title("Kotlin Demo").version("v0.0.1").description("API for Swagger"))
            .components(Components().addSecuritySchemes("Bearer Token", securityScheme))
            .addSecurityItem(securityRequirement)
    }

    @Bean
    fun customize(): OperationCustomizer {
        return OperationCustomizer { operation, handlerMethod ->
            val exceptionResponse = handlerMethod.getMethodAnnotation(SwaggerApiExceptionResponse::class.java)
            if (exceptionResponse != null) {
                createExceptionResponses(operation, exceptionResponse.exceptions)
            }
            operation
        }
    }

    private fun createExceptionResponses(
        operation: Operation,
        exceptionEnums: Array<ExceptionEnum>,
    ) {
        val responses = operation.responses
        val exampleHolderMap =
            exceptionEnums.map { exceptionEnum ->
                ExampleHolder(
                    holder = Example().value(ExceptionResponseDto(exceptionEnum.status, exceptionEnum.code)),
                    name = exceptionEnum.code,
                    code = exceptionEnum.status.toString(),
                )
            }.groupBy { exampleHolder -> exampleHolder.code }

        addApiResponses(responses, exampleHolderMap)
    }

    private fun addApiResponses(
        responses: ApiResponses,
        exampleHolderMap: Map<String, List<ExampleHolder>>,
    ) {
        exampleHolderMap.forEach { (status, exampleHolders) ->
            val content = Content()
            val mediaType = MediaType()
            val apiResponse = ApiResponse()

            exampleHolders.forEach { exampleHolder ->
                mediaType.addExamples(exampleHolder.name, exampleHolder.holder)
            }
            content.addMediaType("application/json", mediaType)
            apiResponse.content = content
            responses.addApiResponse(status, apiResponse)
        }

        responses.addApiResponse("500", defaultExceptionResponse())
    }

    private fun defaultExceptionResponse(): ApiResponse {
        val content = Content()
        val mediaType = MediaType()
        val apiResponse = ApiResponse()

        val exampleHolder =
            ExampleHolder(
                holder =
                    Example().value(
                        ExceptionResponseDto(
                            ExceptionEnum.INTERNAL_SERVER_ERROR.status,
                            ExceptionEnum.INTERNAL_SERVER_ERROR.code,
                        ),
                    ),
                name = ExceptionEnum.INTERNAL_SERVER_ERROR.code,
                code = ExceptionEnum.INTERNAL_SERVER_ERROR.status.toString(),
            )

        mediaType.addExamples(exampleHolder.name, exampleHolder.holder)
        content.addMediaType("application/json", mediaType)
        apiResponse.content = content

        return apiResponse
    }

    class ExampleHolder(
        val holder: Example,
        val name: String,
        val code: String,
    )
}
