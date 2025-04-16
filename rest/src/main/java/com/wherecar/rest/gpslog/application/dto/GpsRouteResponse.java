package com.wherecar.rest.gpslog.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GpsRouteResponse {
    private String mdn;
    private List<GpsPoint> route;
}
