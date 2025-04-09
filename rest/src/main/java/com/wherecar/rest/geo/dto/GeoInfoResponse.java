package com.wherecar.rest.geo.dto;

import com.wherecar.rest.dto.ControlGeoList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoInfoResponse {
    private String rstCd;           // 결과 코드
    private String rstMsg;          // 결과 메시지
    private String mdn;             // 차량 번호
    private String oTime;           // 발생일시
    private String ctrCnt;          // 제어명령 개수
    private String geoCnt;          // 지오펜싱 설정 개수

    private List<ControlGeoList> ctrList; // 제어 리스트

    private List<GeoList> geoList;        // 지오펜스 리스트
}
