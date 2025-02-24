package demo.kotlinboilerplate.common.exception

enum class ExceptionEnum(val errorCode: String, val message: String, val status: Int) {
    BAD_REQUEST("ERROR_400_001", "잘못된 요청입니다.", 400),
    UNAUTHORIZED("ERROR_401_001", "인증이 필요합니다.", 401),
    FORBIDDEN("ERROR_403_001", "요청이 거부되었습니다.", 403),
    NOT_FOUND("ERROR_404_001","요청 자원을 찾을 수 없습니다.", 404),
    INTERNAL_ERROR("ERROR_500_001", "서버 내부 오류가 발생했습니다.", 500)
}