package com.wherecar.collector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 온 오프 로그 요청 폼
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogRequest {

    private String mdn;     // 차량 번호.       차량(단말) 식별 key
    private String tid;     // 터미널 아이디.    차량 관제는 'A001'로 고정
    private Integer mid;     // 제조사 아이디.    CNSLink는 '6' 값 사용
    private Integer pv;      // 패킷 버전.       범위: 0 ~ 65535, M2MM 버전이 5이므로 '5'로 고정
    private Integer did;     // 디바이스 아이디.   GPS로만 운영함으로 '1'로 고정

    @JsonProperty("onTime")
    private LocalDateTime onTime;  // 차량 시동 On 시간. 'yyyyMMddHHmmss'

    @JsonProperty("offTime")
    private LocalDateTime offTime; // 차량 시동 Off 시간. 'yyyyMMddHHmmss'
    private String gcd;     // GPS 상태.         'A': 정상, 'V': 비정상, '0': 미장착, 'P': 시동 ON 시 GPS 정보가 비정상
    private Integer lat;     // GPS 위도.       위도 X 1000000 계산한 값(소수점 6자리)
    private Integer lon;     // GPS 경도.       경도 X 1000000 계산한 값(소수점 6자리)
    private Integer ang;     // 방향.           범위: 0 ~ 360
    private Integer spd;     // 속도.           범위: 0 ~ 255(단위: km/h)
    private Integer sum;     // 누적 주행 거리.  범위: 0 ~ 9999999(단위: m)

}
