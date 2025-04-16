package com.wherecar.rest.gpslog.application;

import com.wherecar.rest.gpslog.application.dto.GpsLogResponse;
import com.wherecar.rest.gpslog.application.dto.GpsRouteResponse;

import java.time.LocalDateTime;

public interface GpsLogService {

    GpsLogResponse getLatestLocation(String mdn);

    GpsRouteResponse getRoute(String mdn, LocalDateTime startTime, LocalDateTime endTime);

}
