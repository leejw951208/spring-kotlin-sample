package demo.kotlinboilerplate.common.exception

data class ExceptionResponseDto(
    val status: Int,
    val code: String,
    val message: String,
)
