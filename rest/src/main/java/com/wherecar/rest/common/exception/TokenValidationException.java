package com.wherecar.rest.common.exception;

public class TokenValidationException extends RuntimeException {
    public TokenValidationException(String message) {
        super(message);
    }
}
