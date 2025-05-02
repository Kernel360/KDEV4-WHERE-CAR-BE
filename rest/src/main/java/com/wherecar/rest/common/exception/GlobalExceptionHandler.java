package com.wherecar.rest.common.exception;

import com.wherecar.rest.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
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

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return BaseResponse.validationErrors(errors);
    }

}
