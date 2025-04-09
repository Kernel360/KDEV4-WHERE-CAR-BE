package com.wherecar.rest.geo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoInfoDTO {
    private String geoEventType;
    private String geoRange;
    private Integer latitude;
    private Integer longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
