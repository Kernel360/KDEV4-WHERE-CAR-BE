package com.wherecar.rest.emulauth.hubtmp.token.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmulTokenResponse {
    private final String rstCd;
    private final String rstMsg;
    private final String mdn;
}
