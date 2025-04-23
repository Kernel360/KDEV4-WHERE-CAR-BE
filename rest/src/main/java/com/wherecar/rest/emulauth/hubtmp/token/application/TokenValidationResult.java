package com.wherecar.rest.emulauth.hubtmp.token.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenValidationResult {
    SUCCESS("000", "Success"),
    MISSING("200", "Missing Token."),
    INVALID("201", "Invalid Token."),
    UNUSABLE("202", "Unusable Token."); // 나중에 추가 가능

    private final String rstCd;
    private final String rstMsg;
}
