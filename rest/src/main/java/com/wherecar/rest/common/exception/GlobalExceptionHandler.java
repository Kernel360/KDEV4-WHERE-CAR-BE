package com.wherecar.rest.common.exception;

import com.wherecar.rest.common.response.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public BaseResponse<Void> handleDuplicateEmailException(DuplicateEmailException e) {
        return BaseResponse.badRequest(e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleException(Exception e) {
        return BaseResponse.badRequest(e.getMessage());
    }
}
