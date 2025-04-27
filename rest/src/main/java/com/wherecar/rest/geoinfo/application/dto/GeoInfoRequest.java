package com.wherecar.rest.geoinfo.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class GeoInfoRequest {

    // TODO 지오펜스는 나중에?

    private String name;
    private String geoEventType;
    private String geoRange;
    private Double latitude;
    private Double longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
