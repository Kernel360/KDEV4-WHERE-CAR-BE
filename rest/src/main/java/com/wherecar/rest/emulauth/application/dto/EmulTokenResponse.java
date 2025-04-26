package com.wherecar.rest.emulauth.application.dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmulTokenResponse {

    private String rstCd;
    private String rstMsg;
    private String mdn;
    private String token;
    private String exPeriod;

}
