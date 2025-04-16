package com.wherecar.rest.service;

import com.wherecar.rest.dto.GpsLogResponse;
import com.wherecar.rest.dto.GpsRouteResponse;

import java.time.LocalDateTime;

public interface GpsLogService {

    GpsLogResponse getLatestLocation(String mdn);

    GpsRouteResponse getRoute(String mdn, LocalDateTime startTime, LocalDateTime endTime);

}
