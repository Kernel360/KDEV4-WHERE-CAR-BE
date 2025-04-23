package com.wherecar.rest.emulauth.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmulTokenRequest {

    private String mdn;
    private String tid;
    private String mid;
    private String pv;
    private String did;
    private String dFWVer;
}

