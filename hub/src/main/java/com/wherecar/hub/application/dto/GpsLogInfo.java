package com.wherecar.hub.application.dto;

import lombok.*;

// 주기 정보 리스트
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsLogInfo {

    private String sec;     // 발생 시간 '초'
    private String gcd;     // GPS 상태.       'A': 정상, 'V': 비정상, '0': 미장착
    private String lat;     // GPS 위도.       위도 X 1000000 계산한 값(소수점 6자리)
    private String lon;     // GPS 경도.       경도 X 1000000 계산한 값(소수점 6자리)
    private String ang;     // 방향.           범위: 0 ~ 360
    private String spd;     // 속도.           범위: 0 ~ 255(단위: km/h)
    private String sum;     // 누적 주행 거리.  범위: 0 ~ 9999999(단위: m)
    private String bat;     // 배터리 전압.     범위: 0 ~ 9999(실제 값 X 10, 단위: V)

    @Override
    public String toString() {
        return "GpsLogInfo{" +
                "sec='" + sec + '\'' +
                ", gcd='" + gcd + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", ang='" + ang + '\'' +
                ", spd='" + spd + '\'' +
                ", sum='" + sum + '\'' +
                ", bat='" + bat + '\'' +
                '}';
    }
}
