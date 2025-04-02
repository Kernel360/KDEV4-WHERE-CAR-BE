package com.wherecar.rest.dto;

import com.wherecar.rest.domain.GeoInfo;
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
    private Integer mdn;
    private Double angle;
    private String evaluate_value;
    private String gps_condition;
    private Integer latitude;
    private Integer longitude;
    private String o_time;
    private Double speed;
    private Double sum;
    private GeoInfo geoInfo;
}
