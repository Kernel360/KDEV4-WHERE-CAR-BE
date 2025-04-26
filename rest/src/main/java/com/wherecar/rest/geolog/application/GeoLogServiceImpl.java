package com.wherecar.rest.geolog.application;

import com.wherecar.rest.car.domain.Car;
import com.wherecar.rest.car.infrastructure.CarReader;
import com.wherecar.rest.geolog.application.dto.GeoLogRequest;
import com.wherecar.rest.geolog.application.dto.GeoLogResponse;
import com.wherecar.rest.geolog.domain.GeoLog;
import com.wherecar.rest.geolog.domain.GeoLogFactory;
import com.wherecar.rest.geolog.infrastructure.GeoLogReader;
import com.wherecar.rest.geolog.infrastructure.GeoLogStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GeoLogServiceImpl implements GeoLogService {

    private final GeoLogFactory geoLogFactory;

    private final CarReader carReader;
    private final GeoLogStore geoLogStore;
    private final GeoLogReader geoLogReader;

    @Override
    @Transactional(readOnly = true)
    public List<GeoLogResponse> getGeoLogsByCarId(Long carId) {
        log.info("[GeoLog][GeoLogServiceImpl][getGeoLogsByCarId] 시작 | carId = {}", carId);

        Car car = carReader.getCarById(carId);
        List<GeoLog> geoLogs = geoLogReader.getGeoLogsByMdn(car.getMdn());

        List<GeoLogResponse> geoLogResponseList = geoLogFactory.toGeoLogListResponse(geoLogs);
        log.info("[GeoLog][GeoLogServiceImpl][getGeoLogsByCarId] 종료 | geoLogResponseList = {}", geoLogResponseList);

        return geoLogResponseList;
    }

    @Override
    @Transactional(readOnly = true)
    public GeoLogResponse getGeoLog(Long geoLogId) {
        log.info("[GeoLog][GeoLogServiceImpl][getGeoLog] 시작 | geoLogId = {}", geoLogId);

        GeoLog geoLog = geoLogReader.getGeoLogById(geoLogId);

        GeoLogResponse geoLogResponse = geoLogFactory.toGeoLogResponse(geoLog);
        log.info("[GeoLog][GeoLogServiceImpl][getGeoLog] 종료 | geoLogResponse = {}", geoLogResponse);

        return geoLogResponse;
    }

    @Override
    public GeoLogResponse updateGeoLog(Long geoLogId, GeoLogRequest geoLogRequest) {
        log.info("[GeoLog][GeoLogServiceImpl][updateGeoLog] 시작 | geoLogId = {}, geoLogRequest = {}", geoLogId, geoLogRequest);

        GeoLog geoLog = geoLogReader.getGeoLogById(geoLogId);
        geoLog.updateGeoLog(geoLogRequest);
        geoLog = geoLogStore.store(geoLog);
        GeoLogResponse geoLogResponse = geoLogFactory.toGeoLogResponse(geoLog);
        log.info("[GeoLog][GeoLogServiceImpl][updateGeoLog] 종료 | geoLogResponse = {}", geoLogResponse);

        return geoLogResponse;
    }

    @Override
    public void deleteGeoLog(Long geoLogId) {
        log.info("[GeoLog][GeoLogServiceImpl][deleteGeoLog] 시작 | geoLogId = {}", geoLogId);

        geoLogStore.delete(geoLogId);
        log.info("[GeoLog][GeoLogServiceImpl][deleteGeoLog] 종료");
    }
}
