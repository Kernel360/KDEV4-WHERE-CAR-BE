package com.wherecar.collector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 주기 정보 리스트
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsLogInfo {

    String sec;     // 발생 시간 '초'
    String gcd;     // GPS 상태.       'A': 정상, 'V': 비정상, '0': 미장착
    Integer lat;     // GPS 위도.       위도 X 1000000 계산한 값(소수점 6자리)
    Integer lon;     // GPS 경도.       경도 X 1000000 계산한 값(소수점 6자리)
    Integer ang;     // 방향.           범위: 0 ~ 360
    Integer spd;     // 속도.           범위: 0 ~ 255(단위: km/h)
    Integer sum;     // 누적 주행 거리.  범위: 0 ~ 9999999(단위: m)
    Integer bat;     // 배터리 전압.     범위: 0 ~ 9999(실제 값 X 10, 단위: V)
}
