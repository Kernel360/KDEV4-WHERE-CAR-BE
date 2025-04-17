package com.wherecar.rest.common.response;

public enum HttpStatusCode {

    // 성공 응답
    OK(200, "요청이 성공적으로 처리되었습니다."),
    CREATED(201, "리소스가 성공적으로 생성되었습니다."),
    ACCEPTED(202, "요청이 성공적으로 수신되었으나 아직 처리되지 않았습니다."),
    NO_CONTENT(204, "요청에 대해 응답할 콘텐츠가 없습니다."),


    // 클라이언트 에러 응답
    BAD_REQUEST(400, "잘못된 요청입니다."),
    UNAUTHORIZED(401, "인증되지 않은 요청입니다."),
    FORBIDDEN(403, "권한이 없는 요청입니다."),
    NOT_FOUND(404, "요청한 리소스를 찾을 수 없습니다."),

    // 서버 에러 응답
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류입니다.");

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


