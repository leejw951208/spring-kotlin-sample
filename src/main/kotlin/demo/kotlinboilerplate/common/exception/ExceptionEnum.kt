package demo.kotlinboilerplate.common.exception

enum class ExceptionEnum(val code: String, val message: String, val status: Int) {
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버에서 오류가 발생했습니다.", 500),

    BAD_REQUEST("BAD_REQUEST", "잘못된 요청입니다.", 400),
    INVALID_PARAMETER("INVALID_PARAMETER", "유효하지 않은 파라미터입니다.", 400),
    INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 토큰입니다.", 400),
    MISSING_PARAMETER("MISSING_PARAMETER", "요청에 필요한 파리머터를 찾을 수 없습니다.", 400),

    UNAUTHORIZED("UNAUTHORIZED", "잘못된 인증 정보 입니다.", 401),
    EXPIRED_TOKEN("EXPIRED_TOKEN", "만료된 토큰입니다.", 401),

    FORBIDDEN("FORBIDDEN", "요청 리소스에 접근 권한이 없습니다.", 403),

    NOT_FOUND_USER("NOT_FOUND_USER", "사용자 정보를 찾을 수 없습니다.", 404),
    NOT_FOUND_TOKEN("NOT_FOUND_TOKEN", "토큰을 찾을 수 없습니다.", 404),
}
