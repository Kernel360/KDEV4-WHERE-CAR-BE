package com.wherecar.rest.geolog.application.dto;

import com.wherecar.rest.geoinfo.application.dto.GeoInfoResponse;
import com.wherecar.rest.gpslog.domain.constant.GpsConditionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoLogResponse {
    private Long id;
    private String mdn;
    private Integer angle;
    private String evaluateValue;
    private GpsConditionType gpsCondition;
    private Double latitude;
    private Double longitude;
    private String oTime;
    private Double speed;
    private Double sum;
    private GeoInfoResponse geoInfoResponse;
}
