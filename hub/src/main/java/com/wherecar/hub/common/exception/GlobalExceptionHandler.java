package com.wherecar.hub.common.exception;

import com.wherecar.hub.carlog.application.dto.CarLogResponse;
import com.wherecar.hub.common.constant.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.wherecar.hub")
public class GlobalExceptionHandler {

    // TODO hub의 컨트롤러에서 리턴 타입이 정해지면 여기도 리턴 타입 다시 수정하기

    @ExceptionHandler
    public void handleValidationExceptions(MethodArgumentNotValidException e) {

        e.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            log.info("Validation 실패: {}", errorMessage);
        });

    }

    @ExceptionHandler
    public void handleException(Exception e) {

        log.error("An unexpected error occurred: ", e);
    }
}
