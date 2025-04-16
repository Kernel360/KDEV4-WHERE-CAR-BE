package com.wherecar.rest.geoinfo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoInfoResponse {
    private Long id;
    private String name;
    private String geoEventType;
    private String geoRange;
    private Double latitude;
    private Double longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
