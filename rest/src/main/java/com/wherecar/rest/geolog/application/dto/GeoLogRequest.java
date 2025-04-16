package com.wherecar.rest.geolog.application.dto;

import com.wherecar.rest.geoinfo.application.dto.GeoInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoLogRequest {
    private Long id;
    private String mdn;
    private Integer angle;
    private String evaluateValue;
    private String gpsCondition;
    private Double latitude;
    private Double longitude;
    private Integer speed;
    private Integer sum;
    private GeoInfoResponse geoInfoResponse;
}
