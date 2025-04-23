package com.wherecar.rest.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    // ====== 성공 응답 ======

    public static <T> ResponseEntity<BaseResponse<T>> ok() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(T data) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(data, HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

    public static <T> ResponseEntity<BaseResponse<T>> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponse<>(data, HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value()));
    }

    public static <T> ResponseEntity<BaseResponse<T>> accepted(String message) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new BaseResponse<>(message, HttpStatus.ACCEPTED.value()));
    }

    public static <T> ResponseEntity<BaseResponse<T>> noContent(String message) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new BaseResponse<>(message, HttpStatus.NO_CONTENT.value()));
    }

    // ====== 클라이언트 오류 응답 ======

    public static <T> ResponseEntity<BaseResponse<T>> badRequest(String message) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(message, HttpStatus.BAD_REQUEST.value()));
    }

    public static <T> ResponseEntity<BaseResponse<T>> unauthorized(String message) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponse<>(message, HttpStatus.UNAUTHORIZED.value()));
    }

    public static <T> ResponseEntity<BaseResponse<T>> forbidden(String message) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new BaseResponse<>(message, HttpStatus.FORBIDDEN.value()));
    }

    public static <T> ResponseEntity<BaseResponse<T>> notFound(String message) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<>(message, HttpStatus.NOT_FOUND.value()));
    }

    // ====== 서버 오류 응답 ======

    public static <T> ResponseEntity<BaseResponse<T>> internalServerError(String message) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse<>(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    // ====== JSON 출력 ======

    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"data\":null,\"message\":\"서버 내부 오류입니다(JSON 변환).\",\"statusCode\":500}";
        }
    }
}
