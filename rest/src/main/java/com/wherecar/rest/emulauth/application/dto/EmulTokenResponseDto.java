package com.wherecar.rest.emulauth.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmulTokenResponseDto {

    private final String rstCd;
    private final String rstMsg;
    private final String mdn;
    private final String token;
    private final String exPeriod;

    public EmulTokenResponseDto(String rstCd, String rstMsg, String mdn, String token, String exPeriod) {
        this.rstCd = rstCd;
        this.rstMsg = rstMsg;
        this.mdn = mdn;
        this.token = token;
        this.exPeriod = exPeriod;
    }

}
