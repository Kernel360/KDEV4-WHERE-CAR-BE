package com.wherecar.rest.gpslog.application;

import com.wherecar.rest.gpslog.application.dto.GpsLogResponse;
import com.wherecar.rest.gpslog.application.dto.GpsPoint;
import com.wherecar.rest.gpslog.application.dto.GpsRouteResponse;
import com.wherecar.rest.gpslog.domain.GpsLog;
import com.wherecar.rest.gpslog.domain.GpsLogFactory;
import com.wherecar.rest.gpslog.infrastructure.GpsLogReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GpsLogServiceImpl implements GpsLogService {

    private final GpsLogReader gpsLogReader;
    private final GpsLogFactory gpsFactory;

    @Override
    public GpsLogResponse getLatestGpsLogByMdn(String mdn) {
        log.info("[GpsLog][GpsLogServiceImpl][getLatestGpsLogByMdn] 시작 | mdn = {}", mdn);

        GpsLog gpslog = gpsLogReader.findTopByMdnOrderByTimestampDesc(mdn);

        GpsLogResponse gpsLogResponse = gpsFactory.toGpsLogResponse(gpslog);
        log.info("[GpsLog][GpsLogServiceImpl][getLatestGpsLogByMdn] 종료 | gpsLogResponse = {}", gpsLogResponse);

        return gpsLogResponse;
    }

    @Override
    public GpsRouteResponse getGpsPointsByMdn(String mdn, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("[GpsLog][GpsLogServiceImpl][getGpsPointsByMdn] 시작 | mdn = {}, startTime = {}, endTime = {}", mdn, startTime, endTime);

        List<GpsLog> route = gpsLogReader.getGpsPointsByTimestamp(mdn, startTime, endTime);
        List<GpsPoint> gpsPoints = gpsFactory.route(route);

        GpsRouteResponse gpsRouteResponse = gpsFactory.toRouteResponse(gpsPoints, mdn);
        log.info("[GpsLog][GpsLogServiceImpl][getGpsPointsByMdn] 종료 | gpsRouteResponse = {}", gpsRouteResponse);

        return gpsRouteResponse;
    }

}
