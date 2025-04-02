package com.wherecar.rest.service;

import com.wherecar.rest.domain.GpsLog;
import com.wherecar.rest.dto.*;
import com.wherecar.rest.repository.GpsLogRepository;
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

    private final GpsLogRepository gpsLogRepository;

    @Override
    public GpsLogResponse getLatestLocation(String mdn) {

        GpsLog gpsLog = gpsLogRepository.findTopByCar_MdnOrderByTimestampDesc(mdn)
                .orElseThrow(() -> new RuntimeException("해당 차량의 GPS 로그가 없습니다."));

        return GpsLogResponse.builder()
                .mdn(gpsLog.getCar().getMdn())
                .longitude(gpsLog.getLongitude())
                .latitude(gpsLog.getLatitude())
                .timestamp(gpsLog.getTimestamp())
                .build();

    }

    @Override
    public GpsRouteResponse getRoute(String mdn, LocalDateTime startTime, LocalDateTime endTime) {
        List<GpsLog> gpsLogs = gpsLogRepository.findByCar_MdnAndTimestampBetweenOrderByTimestamp(mdn, startTime, endTime);

        List<GpsPoint> route = gpsLogs.stream()
                .map(gpsLog -> GpsPoint.builder()
                        .latitude(gpsLog.getLatitude())
                        .longitude(gpsLog.getLongitude())
                        .timestamp(gpsLog.getTimestamp())
                        .build()
                )
                .toList();

        return GpsRouteResponse.builder()
                .mdn(mdn)
                .route(route)
                .build();

    }

}
