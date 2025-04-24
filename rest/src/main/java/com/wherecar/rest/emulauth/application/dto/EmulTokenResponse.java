package com.wherecar.rest.emulauth.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmulTokenResponse {

    private final String rstCd;
    private final String rstMsg;
    private final String mdn;
    private final String token;
    private final String exPeriod;

}
