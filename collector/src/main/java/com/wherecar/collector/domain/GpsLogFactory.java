package com.wherecar.collector.domain;

import com.wherecar.collector.application.dto.GpsLogInfo;
import com.wherecar.collector.application.dto.GpsLogRequest;
import com.wherecar.collector.domain.constant.GpsConditionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class GpsLogFactory {

    public GpsLog toGpsLog(GpsLogRequest gpsLogRequest, GpsLogInfo gpsLogInfo, DateTimeFormatter timestampFormatter) {

        LocalDateTime timestamp = GpsLog.getTimestamp(gpsLogRequest.getOTime(), gpsLogInfo.getSec(), timestampFormatter);
        Double doubleLatitude = GpsLog.parseLatLon(gpsLogInfo.getLat());
        Double doubleLongitude = GpsLog.parseLatLon(gpsLogInfo.getLon());
        GpsConditionType gpsCondition = GpsLog.getGpsConditionType(gpsLogInfo.getGcd());

        return GpsLog.builder()
                .mdn(gpsLogRequest.getMdn())
                .timestamp(timestamp)
                .gpsCondition(gpsCondition)
                .latitude(doubleLatitude)
                .longitude(doubleLongitude)
                .angle(Integer.parseInt(gpsLogInfo.getAng()))
                .speed(Integer.parseInt(gpsLogInfo.getSpd()))
                .sum(Integer.parseInt(gpsLogInfo.getSum()))
                .build();
    }
}
