package com.wherecar.collector.common.exception;

import com.wherecar.collector.carlog.application.dto.CarLogResponse;
import com.wherecar.collector.common.constant.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.wherecar.collector")
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CarLogResponse> handleValidationExceptions(MethodArgumentNotValidException e) {

        e.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            log.info("Validation 실패: {}", errorMessage);
        });

        CarLogResponse carLogResponse = CarLogResponse.builder()
                .rstCd(ResponseCode.REQUIRED_PARAMETER_ERROR.getCode())
                .rstMsg(ResponseCode.REQUIRED_PARAMETER_ERROR.getMessage())
                .mdn(null)
                .build();

        return ResponseEntity.ok().body(carLogResponse);
    }

    @ExceptionHandler
    public ResponseEntity<CarLogResponse> handleException(Exception e) {

        log.error("An unexpected error occurred: ", e);

        CarLogResponse carLogResponse = CarLogResponse.builder()
                .rstCd(ResponseCode.UNDEFINED_ERROR.getCode())
                .rstMsg(ResponseCode.UNDEFINED_ERROR.getMessage())
                .mdn(null)
                .build();

        return ResponseEntity.ok().body(carLogResponse);
    }

}

