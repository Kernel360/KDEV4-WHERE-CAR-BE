package com.wherecar.rest.gpslog.application;

import com.wherecar.rest.gpslog.application.dto.GpsLogResponse;
import com.wherecar.rest.gpslog.application.dto.GpsRouteResponse;

import java.time.LocalDateTime;

public interface GpsLogService {

    GpsLogResponse getLatestGpsLogByMdn(String mdn);

    GpsRouteResponse getGpsPointsByMdn(String mdn, LocalDateTime startTime, LocalDateTime endTime);

}
