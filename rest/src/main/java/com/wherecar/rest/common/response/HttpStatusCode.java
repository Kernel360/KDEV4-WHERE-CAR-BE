package com.wherecar.rest.common.response;

public enum HttpStatusCode {

    // 성공 응답
    OK(200, "요청이 성공적으로 처리되었습니다."),
    CREATED(201, "리소스가 성공적으로 생성되었습니다."),
    ACCEPTED(202, "요청이 성공적으로 수신되었으나 아직 처리되지 않았습니다."),
    NO_CONTENT(204, "요청에 대해 응답할 콘텐츠가 없습니다."),

    // 리다이렉션 응답
    MOVED_PERMANENTLY(301, "요청한 리소스가 영구적으로 다른 위치로 이동되었습니다."),
    FOUND(302, "요청한 리소스가 임시적으로 다른 위치에 있습니다."),

    // 클라이언트 에러 응답
    BAD_REQUEST(400, "잘못된 요청입니다."),
    UNAUTHORIZED(401, "인증되지 않은 요청입니다."),
    FORBIDDEN(403, "권한이 없는 요청입니다."),
    NOT_FOUND(404, "요청한 리소스를 찾을 수 없습니다."),

    // 서버 에러 응답
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류입니다."),
    NOT_IMPLEMENTED(501, "서버에서 요청한 메소드를 지원하지 않습니다."),
    BAD_GATEWAY(502, "잘못된 게이트웨이 응답을 받았습니다."),
    SERVICE_UNAVAILABLE(503, "서비스를 사용할 수 없습니다."),
    GATEWAY_TIMEOUT(504, "게이트웨이 응답 시간이 초과되었습니다."),

    // 추가적인 상태 코드
    PAYMENT_REQUIRED(402, "결제 필요"),
    METHOD_NOT_ALLOWED(405, "허용되지 않은 메소드입니다."),
    CONFLICT(409, "서버 상태와 충돌이 발생했습니다."),
    LENGTH_REQUIRED(411, "요청에 Content-Length가 필요합니다."),
    URI_TOO_LONG(414, "요청한 URI가 너무 깁니다."),
    UNSUPPORTED_MEDIA_TYPE(415, "지원되지 않는 미디어 타입입니다."),
    EXPECTATION_FAILED(417, "서버가 요청한 예상값을 만족하지 못했습니다."),
    IM_USED(226, "요청이 델타 인코딩 방식으로 처리되었습니다.");

    private final int statusCode;
    private final String message;

    // 생성자
    HttpStatusCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // 상태 코드 반환
    public int getStatusCode() {
        return statusCode;
    }

    // 상태 메시지 반환
    public String getMessage() {
        return message;
    }
}


