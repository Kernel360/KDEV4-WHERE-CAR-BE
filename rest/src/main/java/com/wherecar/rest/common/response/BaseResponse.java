package com.wherecar.rest.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseResponse<T> {
    private T data;
    private String message;
    private int statusCode;

    private static final ObjectMapper objectMapper = new ObjectMapper();  // ObjectMapper 재사용

    public BaseResponse(T data, HttpStatusCode httpStatusCode) {
        this.data = data;
        this.message = httpStatusCode.getMessage();
        this.statusCode = httpStatusCode.getStatusCode();
    }

    public BaseResponse(HttpStatusCode httpStatusCode) {
        this.data = null;
        this.message = httpStatusCode.getMessage();
        this.statusCode = httpStatusCode.getStatusCode();
    }

    public BaseResponse(String message, HttpStatusCode httpStatusCode) {
        this.data = null;
        this.message = message;
        this.statusCode = httpStatusCode.getStatusCode();
    }

    // 200 OK
    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(data, HttpStatusCode.OK);
    }

    // 201 Created
    public static <T> BaseResponse<T> created(T data) {
        return new BaseResponse<>(data, HttpStatusCode.CREATED);
    }

    // 204 No Content
    public static <T> BaseResponse<T> noContent() {
        return new BaseResponse<>(null, HttpStatusCode.NO_CONTENT);
    }

    // 400 Bad Request
    public static <T> BaseResponse<T> badRequest(String message) {
        return new BaseResponse<>(message, HttpStatusCode.BAD_REQUEST);
    }

    // 401 Unauthorized
    public static <T> BaseResponse<T> unauthorized(String message) {
        return new BaseResponse<>(message, HttpStatusCode.UNAUTHORIZED);
    }

    // 404 Not Found
    public static <T> BaseResponse<T> notFound(String message) {
        return new BaseResponse<>(message, HttpStatusCode.NOT_FOUND);
    }

    // 409 Conflict
    public static <T> BaseResponse<T> conflict(String message) {
        return new BaseResponse<>(message, HttpStatusCode.CONFLICT);
    }

    // 500 Internal Server Error
    public static <T> BaseResponse<T> internalServerError(String message) {
        return new BaseResponse<>(message, HttpStatusCode.INTERNAL_SERVER_ERROR);
    }

    // 202 Accepted
    public static <T> BaseResponse<T> accepted(String message) {
        return new BaseResponse<>(null, HttpStatusCode.ACCEPTED);
    }

    // 403 Forbidden
    public static <T> BaseResponse<T> forbidden(String message) {
        return new BaseResponse<>(message, HttpStatusCode.FORBIDDEN);
    }

    // 405 Method Not Allowed
    public static <T> BaseResponse<T> methodNotAllowed(String message) {
        return new BaseResponse<>(message, HttpStatusCode.METHOD_NOT_ALLOWED);
    }


    // 503 Service Unavailable
    public static <T> BaseResponse<T> serviceUnavailable(String message) {
        return new BaseResponse<>(message, HttpStatusCode.SERVICE_UNAVAILABLE);
    }

    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);  // JSON 형식으로 반환
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"data\":null,\"message\":\"서버 내부 오류입니다(JSON 변환).\",\"statusCode\":500}";  // 예외 발생 시 빈 객체 반환
        }
    }
}
