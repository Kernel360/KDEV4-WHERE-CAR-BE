package com.wherecar.rest.common.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("이미 등록된 이메일입니다: " + email);
    }
}
