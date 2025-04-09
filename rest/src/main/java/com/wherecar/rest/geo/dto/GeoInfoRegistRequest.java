package com.wherecar.rest.geo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GeoInfoRegistRequest {
    private String geoEventType;
    private String geoRange;
    private Integer latitude;
    private Integer longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
