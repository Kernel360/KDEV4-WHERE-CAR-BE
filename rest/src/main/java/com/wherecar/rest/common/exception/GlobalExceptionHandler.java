package com.wherecar.rest.common.exception;

import com.wherecar.rest.common.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e) {
        return BaseResponse.badRequest(e.getMessage());
    }
}
