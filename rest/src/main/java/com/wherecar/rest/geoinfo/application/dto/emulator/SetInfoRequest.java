package com.wherecar.rest.geoinfo.application.dto.emulator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetInfoRequest {

    @NotBlank(message = "mdn은 필수입니다.")
    private String mdn;     // 차량 번호

    @NotBlank(message = "tid는 필수입니다.")
    private String tid;     // 터미널 아이디

    @NotBlank(message = "mid는 필수입니다.")
    private String mid;     // 제조사 아이디

    @Pattern(regexp = "^([0-9]|[1-9][0-9]{1,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$",
            message = "0부터 65535 사이의 숫자여야 합니다.")
    @NotBlank(message = "pv는 필수입니다.")
    private String pv;      // 패킷 버전

    @NotBlank(message = "did는 필수입니다.")
    private String did;     // 디바이스 아이디

    @Pattern(regexp = "^\\d{14}$", message = "yyyyMMddHHmmss 형식의 14자리 숫자여야 합니다.")
    private String onTime;  // 차량 시동 on 시간

    private String dFWVer;  // 디바이스 펌웨어 버전

}
