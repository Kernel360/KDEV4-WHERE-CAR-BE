package com.wherecar.collector.gpslog.application.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

// 주기 정보 리스트
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsLogInfo {

    @Pattern(regexp = "^(0[0-9]|[1-5][0-9])$", message = "00부터 59 사이의 두 자리 숫자여야 합니다.")
    private String sec;     // 발생 시간 '초'.   'ss'.   "sec":"33"

    private String gcd;     // GPS 상태.       'A': 정상, 'V': 비정상, '0': 미장착.   "gcd":"A"

    private String lat;     // GPS 위도.       위도 X 1000000 계산한 값(소수점 6자리).   "lat":"4140338"

    private String lon;     // GPS 경도.       경도 X 1000000 계산한 값(소수점 6자리).   "lon":"217403"

    @Pattern(regexp = "^(0|[1-9]\\d?|1\\d{2}|2\\d{2}|3[0-5][0-9]|36[0-5])$", message = "0부터 365 사이의 숫자여야 합니다.")
    private String ang;     // 방향.           범위: 0 ~ 365.   "ang":"270"

    @Pattern(regexp = "^(0|[1-9]\\d?|1\\d{2}|2[0-4]\\d|25[0-5])$", message = "0부터 255 사이의 숫자여야 합니다.")
    private String spd;     // 속도.           범위: 0 ~ 255(단위: km/h).     "spd":"100"

    @Pattern(regexp = "^(0|[1-9]\\d{0,6})$", message = "0부터 9,999,999 사이의 숫자여야 합니다.")
    private String sum;     // 누적 주행 거리.  범위: 0 ~ 9999999(단위: m).   "sum":"10000"

    @Pattern(regexp = "^(0|[1-9]\\d{0,3})$", message = "0부터 9999 사이의 숫자여야 합니다.")
    private String bat;     // 배터리 전압.     범위: 0 ~ 9999(실제 값 X 10, 단위: V).  "bat":"100"

}
