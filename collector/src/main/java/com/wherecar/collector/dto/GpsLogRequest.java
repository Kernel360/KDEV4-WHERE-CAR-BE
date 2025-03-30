package com.wherecar.collector.dto;

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

    String mdn;     // 차량 번호.       차량(단말) 식별 key
    String tid;     // 터미널 아이디.    차량 관제는 'A001'로 고정
    Integer mid;     // 제조사 아이디.    CNSLink는 '6' 값 사용
    Integer pv;      // 패킷 버전.       범위: 0 ~ 65535, M2MM 버전이 5이므로 '5'로 고정
    Integer did;     // 디바이스 아이디.   GPS로만 운영함으로 '1'로 고정
    LocalDateTime oTime;   // 발생 시간.       'yyyyMMddHHmm'
    Integer cCnt;    // 주기 정보 개수
    List<GpsLogInfo> cList = new ArrayList<>(); // 주기 정보 리스트
}
