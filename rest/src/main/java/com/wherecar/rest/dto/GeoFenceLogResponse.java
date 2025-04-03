package com.wherecar.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoFenceLogResponse {
    private Long id;
    private String mdn;
    private Double angle;
    private String evaluateValue;
    private String gpsCondition;
    private Integer latitude;
    private Integer longitude;
    private String oTime;
    private Double speed;
    private Double sum;
    private GeoInfoDTO geoInfoDTO;
}
