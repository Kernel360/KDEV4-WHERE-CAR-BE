package com.wherecar.rest.gpslog.application.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsRouteResponse {
    private String mdn;
    private List<GpsPoint> route;
}
