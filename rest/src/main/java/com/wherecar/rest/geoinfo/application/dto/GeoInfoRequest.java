package com.wherecar.rest.geoinfo.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class GeoInfoRequest {
    private String name;
    private String geoEventType;
    private String geoRange;
    private Double latitude;
    private Double longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
