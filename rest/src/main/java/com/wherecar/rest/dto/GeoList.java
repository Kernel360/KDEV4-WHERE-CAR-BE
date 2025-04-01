package com.wherecar.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoList {
    private String geoCtrId;        // 지오펜스 아이디
    private String upVal;           // 업데이트 값
    private String geoGrpId;        // 그룹 아이디
    private String geoEventType;    // 이벤트 타입
    private String geoRange;        // 지오펜스 반경
    private Integer latitude;        // 위도
    private Integer longitude;       // 경도
    private LocalDateTime onTime;   // 시작 시간
    private LocalDateTime offTime;  // 종료 시간
}
