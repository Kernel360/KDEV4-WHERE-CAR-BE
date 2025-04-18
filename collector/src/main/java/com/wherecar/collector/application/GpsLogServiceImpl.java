package com.wherecar.collector.application;

import com.wherecar.collector.domain.*;
import com.wherecar.collector.application.dto.GpsLogRequest;
import com.wherecar.collector.infrastructure.infra.CarReader;
import com.wherecar.collector.infrastructure.infra.GpsLogStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GpsLogServiceImpl implements GpsLogService {

    private final CarReader carReader;
    private final GpsLogStore gpsLogStore;

    @Override
    public void receiveGpsLog(GpsLogRequest gpsLogRequest) {

        Car car = carReader.getCarByMdn(gpsLogRequest.getMdn());
        gpsLogStore.storeGpsLogs(gpsLogRequest, car);
    }
}
