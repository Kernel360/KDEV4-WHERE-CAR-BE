package com.wherecar.rest.dto;

import com.wherecar.rest.domain.GeoInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoFenceLogRequest {
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
