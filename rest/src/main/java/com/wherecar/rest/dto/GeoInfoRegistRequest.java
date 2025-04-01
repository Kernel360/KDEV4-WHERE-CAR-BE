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
public class GeoInfoRegistRequest {
    private String geoEventType;
    private String geoRange;
    private String latitude;
    private String longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
