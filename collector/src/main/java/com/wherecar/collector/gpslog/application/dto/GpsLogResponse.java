package com.wherecar.collector.gpslog.application.dto;

import com.wherecar.collector.common.constant.ResponseCode;
import lombok.*;

// 주기 정보 응답 폼
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsLogResponse {

    private String rstCd;    // 결과 코드.    응답 코드 정의 참조.  "rstCd ":"000"
    private String rstMsg;   // 결과 메시지.  응답 코드 정의 참조.   “rstMsg”:”Success”
    private String mdn;      // 차량 번호.    차량(단말) 식별 key.    "mdn":"01234567890"

    // TODO 일단 무조건 성공한다고 가정하고 작성. 그 외의 경우도 생각해 보기
    // TODO mdn이 null인 경우에 대한 처리 필요?
    public static GpsLogResponse getGpsLogResponse(String mdn) {

        return GpsLogResponse.builder()
                .rstCd(ResponseCode.SUCCESS.getCode())
                .rstMsg(ResponseCode.SUCCESS.getMessage())
                .mdn(mdn)
                .build();
    }

}
