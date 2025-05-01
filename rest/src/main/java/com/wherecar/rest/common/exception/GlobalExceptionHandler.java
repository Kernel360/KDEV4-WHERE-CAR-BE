package com.wherecar.rest.common.exception;

import com.wherecar.rest.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(basePackages = "com.wherecar.rest")
public class GlobalExceptionHandler {

    // TODO 검증과 별개의 예외들도 처리하기

    @ExceptionHandler
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e) {

        return BaseResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse<Void>> handleException(AccessDeniedException e) {
        return BaseResponse.forbidden(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException e) {
        e.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            log.info("Validation 실패: {}", errorMessage);
        });

        return BaseResponse.badRequest(e.getMessage());
    }

}
