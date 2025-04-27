package com.wherecar.hub.gpslog.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// 주기 정보 요청 폼
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GpsLogRequest {

    @NotBlank(message = "mdn은 필수입니다.")
    private String mdn;     // 차량 번호.       차량(단말) 식별 key.  "mdn":"01234567890"

    @NotBlank(message = "tid는 필수입니다.")
    private String tid;     // 터미널 아이디.    차량 관제는 'A001'로 고정.   "tid":"A001"

    @NotBlank(message = "mid는 필수입니다.")
    private String mid;     // 제조사 아이디.    CNSLink는 '6' 값 사용.   "mid":"6"

    @Pattern(regexp = "^([0-9]|[1-9][0-9]{1,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$",
            message = "0부터 65535 사이의 숫자여야 합니다.")
    @NotBlank(message = "pv는 필수입니다.")
    private String pv;      // 패킷 버전.       범위: 0 ~ 65535, M2MM 버전이 5이므로 '5'로 고정.   "pv":"5"

    @NotBlank(message = "did는 필수입니다.")
    private String did;     // 디바이스 아이디.   GPS로만 운영함으로 '1'로 고정. "did":"1"

    @JsonProperty("oTime")
    @Pattern(regexp = "^(20\\d{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])([01][0-9]|2[0-3])([0-5][0-9])$", message = "yyyyMMddHHmm 형식이어야 합니다.")
    private String oTime;   // 발생 시간.       'yyyyMMddHHmm'.     "oTime":"202109010920"

    @JsonProperty("cCnt")
    private String cCnt;    // 주기 정보 개수.     "cCnt":"2"

    @Builder.Default
    @JsonProperty("cList")
    private List<GpsLogInfo> cList = new ArrayList<>(); // 주기 정보 리스트

}
