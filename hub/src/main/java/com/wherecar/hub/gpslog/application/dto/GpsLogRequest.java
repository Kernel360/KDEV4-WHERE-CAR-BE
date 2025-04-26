package com.wherecar.hub.gpslog.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String mdn;     // 차량 번호.       차량(단말) 식별 key
    private String tid;     // 터미널 아이디.    차량 관제는 'A001'로 고정
    private String mid;     // 제조사 아이디.    CNSLink는 '6' 값 사용
    private String pv;      // 패킷 버전.       범위: 0 ~ 65535, M2MM 버전이 5이므로 '5'로 고정
    private String did;     // 디바이스 아이디.   GPS로만 운영함으로 '1'로 고정

    @JsonProperty("oTime")
    private String oTime;   // 발생 시간.       'yyyyMMddHHmm'

    @JsonProperty("cCnt")
    private String cCnt;    // 주기 정보 개수

    @Builder.Default
    @JsonProperty("cList")
    private List<GpsLogInfo> cList = new ArrayList<>(); // 주기 정보 리스트

}
