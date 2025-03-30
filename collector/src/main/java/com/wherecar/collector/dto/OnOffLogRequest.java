package com.wherecar.collector.dto;

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
public class OnOffLogRequest {

    String mdn;     // 차량 번호.       차량(단말) 식별 key
    String tid;     // 터미널 아이디.    차량 관제는 'A001'로 고정
    Integer mid;     // 제조사 아이디.    CNSLink는 '6' 값 사용
    Integer pv;      // 패킷 버전.       범위: 0 ~ 65535, M2MM 버전이 5이므로 '5'로 고정
    Integer did;     // 디바이스 아이디.   GPS로만 운영함으로 '1'로 고정
    LocalDateTime onTime;  // 차량 시동 On 시간. 'yyyyMMddHHmmss'
    LocalDateTime offTime; // 차량 시동 Off 시간. 'yyyyMMddHHmmss'
    String gcd;     // GPS 상태.         'A': 정상, 'V': 비정상, '0': 미장착, 'P': 시동 ON 시 GPS 정보가 비정상
    Integer lat;     // GPS 위도.       위도 X 1000000 계산한 값(소수점 6자리)
    Integer lon;     // GPS 경도.       경도 X 1000000 계산한 값(소수점 6자리)
    Integer ang;     // 방향.           범위: 0 ~ 360
    Integer spd;     // 속도.           범위: 0 ~ 255(단위: km/h)
    Integer sum;     // 누적 주행 거리.  범위: 0 ~ 9999999(단위: m)

}
