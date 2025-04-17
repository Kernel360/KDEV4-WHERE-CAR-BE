package com.wherecar.rest.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class BaseResponse<T> {
    private T data;
    private String message;
    private int statusCode;

    private static final ObjectMapper objectMapper = new ObjectMapper();  // ObjectMapper 재사용

    public BaseResponse(T data, String message, int statusCode) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public BaseResponse(String message, int statusCode) {
        this.data = null;
        this.message = message;
        this.statusCode = statusCode;
    }


    // 200 OK
    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<>(HttpStatusCode.OK.getMessage(), HttpStatusCode.OK.getStatusCode());
    }

    // 200 OK
    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(data, HttpStatusCode.OK.getMessage(), HttpStatusCode.OK.getStatusCode());
    }

    // 201 Created
    public static <T> BaseResponse<T> created(T data) {
        return new BaseResponse<>(data, HttpStatusCode.CREATED.getMessage(), HttpStatusCode.CREATED.getStatusCode());
    }

    // 202 Accepted
    public static <T> BaseResponse<T> accepted(String message) {
        return new BaseResponse<>(HttpStatusCode.ACCEPTED.getMessage(), HttpStatusCode.ACCEPTED.getStatusCode());
    }

    // 204 No Content
    public static <T> BaseResponse<T> noContent(String message) {
        return new BaseResponse<>(message, HttpStatusCode.NO_CONTENT.getStatusCode());
    }

    // 400 Bad Request
    public static <T> BaseResponse<T> badRequest(String message) {
        return new BaseResponse<>(message, HttpStatusCode.BAD_REQUEST.getStatusCode());
    }

    // 401 Unauthorized
    public static <T> BaseResponse<T> unauthorized(String message) {
        return new BaseResponse<>(message, HttpStatusCode.UNAUTHORIZED.getStatusCode());
    }

    // 403 Forbidden
    public static <T> BaseResponse<T> forbidden(String message) {
        return new BaseResponse<>(message, HttpStatusCode.FORBIDDEN.getStatusCode());
    }

    // 404 Not Found
    public static <T> BaseResponse<T> notFound(String message) {
        return new BaseResponse<>(message, HttpStatusCode.NOT_FOUND.getStatusCode());
    }


    // 500 Internal Server Error
    public static <T> BaseResponse<T> internalServerError(String message) {
        return new BaseResponse<>(message, HttpStatusCode.INTERNAL_SERVER_ERROR.getStatusCode());
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
