package com.wherecar.rest.common.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, String type, Object identifier) {
        super(String.format("[%s]를 찾을 수 없습니다. (%s: %s)", entityName, type, identifier));
    }
}
