package com.wherecar.collector.application.dto;

import com.wherecar.collector.application.dto.constant.ResponseCode;
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

    // TODO mdn이 null인 경우에 대한 처리 필요?
    // TODO 일단 무조건 성공한다고 가정하고 작성. 그 외의 경우도 생각해 보기
    public static CarLogResponse getCarLogResponse(String mdn) {

        return CarLogResponse.builder()
                .rstCd(ResponseCode.SUCCESS.getCode())
                .rstMsg(ResponseCode.SUCCESS.getMessage())
                .mdn(mdn)
                .build();
    }
}
