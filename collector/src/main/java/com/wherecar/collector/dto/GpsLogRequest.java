package com.wherecar.collector.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 주기 정보 요청 폼
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsLogRequest {

    private String mdn;     // 차량 번호.       차량(단말) 식별 key
    private String tid;     // 터미널 아이디.    차량 관제는 'A001'로 고정
    private Integer mid;     // 제조사 아이디.    CNSLink는 '6' 값 사용
    private Integer pv;      // 패킷 버전.       범위: 0 ~ 65535, M2MM 버전이 5이므로 '5'로 고정
    private Integer did;     // 디바이스 아이디.   GPS로만 운영함으로 '1'로 고정

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonProperty("oTime")
    private LocalDateTime oTime;   // 발생 시간.       'yyyyMMddHHmm'

    @JsonProperty("cCnt")
    private Integer cCnt;    // 주기 정보 개수

    @JsonProperty("cList")
    private List<GpsLogInfo> cList = new ArrayList<>(); // 주기 정보 리스트
}
