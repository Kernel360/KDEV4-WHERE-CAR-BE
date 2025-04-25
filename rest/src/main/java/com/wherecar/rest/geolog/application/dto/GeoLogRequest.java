package com.wherecar.rest.geolog.application.dto;

import com.wherecar.rest.geoinfo.application.dto.GeoInfoResponse;
import com.wherecar.rest.gpslog.domain.constant.GpsConditionType;
import lombok.*;

@Setter
@Getter
@ToString
public class GeoLogRequest {
    private Long id;
    private String mdn;
    private Integer angle;
    private String evaluateValue;
    private GpsConditionType gpsCondition;
    private Double latitude;
    private Double longitude;
    private Integer speed;
    private Integer sum;
    private GeoInfoResponse geoInfoResponse;
}
