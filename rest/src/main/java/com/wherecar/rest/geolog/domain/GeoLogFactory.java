package com.wherecar.rest.geolog.domain;

import com.wherecar.rest.geoinfo.application.dto.GeoInfoResponse;
import com.wherecar.rest.geolog.application.dto.GeoLogResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GeoLogFactory {

    public GeoLogResponse toGeoLogResponse(GeoLog geoLog) {
        return GeoLogResponse.builder()
                .mdn(geoLog.getMdn())
                .angle(geoLog.getAngle())
                .evaluateValue(geoLog.getEvaluateValue())
                .gpsCondition(geoLog.getGpsCondition())
                .latitude(geoLog.getLatitude())
                .longitude(geoLog.getLongitude())
                .oTime(String.valueOf(geoLog.getOTime()))
                .speed(Double.valueOf(geoLog.getSpeed()))
                .sum(Double.valueOf(geoLog.getSum()))
                .geoInfoResponse(GeoInfoResponse.builder()
                        .geoEventType(geoLog.getGeoInfo().getGeoEventType())
                        .geoRange(geoLog.getGeoInfo().getGeoRange())
                        .latitude(geoLog.getGeoInfo().getLatitude())
                        .longitude(geoLog.getGeoInfo().getLongitude())
                        .onTime(geoLog.getGeoInfo().getOnTime())
                        .offTime(geoLog.getGeoInfo().getOffTime())
                        .build())
                .build();
    }

    public List<GeoLogResponse> toGeoLogListResponse(List<GeoLog> geoLogs) {
        return geoLogs.stream()
                .map(this::toGeoLogResponse)
                .collect(Collectors.toList());
    }

}
