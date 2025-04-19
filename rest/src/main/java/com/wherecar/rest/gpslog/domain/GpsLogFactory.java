package com.wherecar.rest.gpslog.domain;

import com.wherecar.rest.gpslog.application.dto.GpsLogResponse;
import com.wherecar.rest.gpslog.application.dto.GpsPoint;
import com.wherecar.rest.gpslog.application.dto.GpsRouteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GpsLogFactory {

    public GpsLogResponse toGpsLogResponse(GpsLog gpsLog) {

        return GpsLogResponse.builder()
                .mdn(gpsLog.getMdn())
                .latitude(gpsLog.getLatitude())
                .longitude(gpsLog.getLongitude())
                .timestamp(gpsLog.getTimestamp())
                .build();

    }

    public List<GpsPoint> route(List<GpsLog> gpsLogs) {

        return gpsLogs.stream()
                .map(gpsLog -> GpsPoint.builder()
                        .latitude(gpsLog.getLatitude())
                        .longitude(gpsLog.getLongitude())
                        .timestamp(gpsLog.getTimestamp())
                        .build()
                )
                .toList();

    }

    public GpsRouteResponse toRouteResponse(List<GpsPoint> route, String mdn) {

        return GpsRouteResponse.builder()
                .mdn(mdn)
                .route(route)
                .build();

    }

}
