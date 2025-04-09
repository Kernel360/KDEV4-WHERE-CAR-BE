package com.wherecar.rest.geo.dto;

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
    private Integer latitude;
    private Integer longitude;
    private Integer speed;
    private Integer sum;
}
