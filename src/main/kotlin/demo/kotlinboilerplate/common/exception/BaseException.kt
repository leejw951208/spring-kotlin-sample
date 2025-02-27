package demo.kotlinboilerplate.common.exception

class BaseException(val exceptionEnum: ExceptionEnum, location: String, stackTrace: String?) : RuntimeException()
