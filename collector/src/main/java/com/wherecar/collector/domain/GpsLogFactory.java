package com.wherecar.collector.domain;

import com.wherecar.collector.application.dto.GpsLogInfo;
import com.wherecar.collector.application.dto.GpsLogRequest;
import com.wherecar.collector.domain.constant.GpsConditionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GpsLogFactory {

    public List<GpsLog> toGpsLogList(GpsLogRequest gpsLogRequest) {

        DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        List<GpsLog> gpsLogList = new ArrayList<>();

        // 0 ~ 59초의 주기 정보 데이터 처리
        for (GpsLogInfo gpsLogInfo : gpsLogRequest.getCList()) {

            LocalDateTime timestamp = GpsLog.getTimestamp(gpsLogRequest.getOTime(), gpsLogInfo.getSec(), timestampFormatter);
            Double doubleLatitude = GpsLog.parseLatLon(gpsLogInfo.getLat());
            Double doubleLongitude = GpsLog.parseLatLon(gpsLogInfo.getLon());
            GpsConditionType gpsCondition = GpsLog.getGpsConditionType(gpsLogInfo.getGcd());

            GpsLog gpsLog = GpsLog.builder()
                    .mdn(gpsLogRequest.getMdn())
                    .timestamp(timestamp)
                    .gpsCondition(gpsCondition)
                    .latitude(doubleLatitude)
                    .longitude(doubleLongitude)
                    .angle(Integer.parseInt(gpsLogInfo.getAng()))
                    .speed(Integer.parseInt(gpsLogInfo.getSpd()))
                    .sum(Integer.parseInt(gpsLogInfo.getSum()))
                    .build();

            gpsLogList.add(gpsLog);
        }

        return gpsLogList;
    }
}
