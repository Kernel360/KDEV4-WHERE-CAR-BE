package com.wherecar.hub.carlog.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

// 온 오프 로그 요청 폼
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarLogRequest {

    private String mdn;     // 차량 번호.       차량(단말) 식별 key.      "mdn":"01234567890"
    private String tid;     // 터미널 아이디.    차량 관제는 'A001'로 고정.   "tid":"A001"
    private String mid;     // 제조사 아이디.    CNSLink는 '6' 값 사용.   “mid":"6"
    private String pv;      // 패킷 버전.       범위: 0 ~ 65535, M2MM 버전이 5이므로 '5'로 고정.   “pv":"5"
    private String did;     // 디바이스 아이디.   GPS로만 운영함으로 '1'로 고정.     "did":"1"

    @JsonProperty("onTime")
    private String onTime;  // 차량 시동 On 시간. 'yyyyMMddHHmmss'.   "onTime":"20210901092000"

    @JsonProperty("offTime")
    private String offTime; // 차량 시동 Off 시간. 'yyyyMMddHHmmss'.  "offTime":""(On),   "offTime":"20210901102000"(Off)

    private String gcd;     // GPS 상태.       'A': 정상, 'V': 비정상, '0': 미장착, 'P': 시동 ON 시 GPS 정보가 비정상. "gcd":"A"
    private String lat;     // GPS 위도.       위도 X 1000000 계산한 값(소수점 6자리).   "lat":"4140338"
    private String lon;     // GPS 경도.       경도 X 1000000 계산한 값(소수점 6자리).   "lon":"217403"
    private String ang;     // 방향.           범위: 0 ~ 365.   "ang":"270"
    private String spd;     // 속도.           범위: 0 ~ 255(단위: km/h).     "spd":"0"
    private String sum;     // 누적 주행 거리.  범위: 0 ~ 9999999(단위: m).   "sum":"10000"

}
