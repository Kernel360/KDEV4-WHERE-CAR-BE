package com.wherecar.collector.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 온 오프 로그 응답 폼
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogResponse {

    private String rstCd;   // 결과 코드.   응답 코드 정의 참조
    private String rstMsg;  // 결과 메시지.  응답 코드 정의 참조
    private String mdn;     // 차량 번호.   차량(단말) 식별 key
}
