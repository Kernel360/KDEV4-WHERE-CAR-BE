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
public class GeoFenceResponse {
    private Long id;
    private String geoEventType;
    private String geoRange;
    private Integer latitude;
    private Integer longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
