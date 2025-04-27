package com.wherecar.rest.emulauth.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmulTokenRequestDto {

    @NotBlank(message = "mdn은 필수입니다.")
    private String mdn;

    @NotBlank(message = "tid는 필수입니다.")
    private String tid;

    @NotBlank(message = "mid는 필수입니다.")
    private String mid;

    @Pattern(regexp = "^([0-9]|[1-9][0-9]{1,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$",
            message = "0부터 65535 사이의 숫자여야 합니다.")
    @NotBlank(message = "pv는 필수입니다.")
    private String pv;

    @NotBlank(message = "did는 필수입니다.")
    private String did;

    private String dFWVer;
}

