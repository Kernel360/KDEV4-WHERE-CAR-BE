package com.wherecar.rest.geolog.application;

import com.wherecar.rest.car.domain.Car;
import com.wherecar.rest.car.infrastructure.infra.CarReader;
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
    public List<GeoLogResponse> getGeoLogByCarId(Long carId) {
        Car car = carReader.getCarById(carId);
        List<GeoLog> geoLogs = geoLogReader.getGeoLogByMdn(car.getMdn());

        log.info(geoLogs.toString());

        return geoLogFactory.toGeoLogListResponse(geoLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public GeoLogResponse getGeoLog(Long geoLogId) {
        GeoLog geoLog = geoLogReader.getGeoLogById(geoLogId);

        return geoLogFactory.toGeoLogResponse(geoLog);
    }

    @Override
    public GeoLogResponse updateGeoLog(Long geoLogId, GeoLogRequest geoLogRequest) {
        GeoLog geoLog = geoLogReader.getGeoLogById(geoLogId);
        geoLog.updateGeoLog(geoLogRequest);
        geoLog = geoLogStore.store(geoLog);
        return geoLogFactory.toGeoLogResponse(geoLog);
    }

    @Override
    public void deleteGeoLog(Long geoLogId) {
        geoLogStore.deleteById(geoLogId);
    }
}
