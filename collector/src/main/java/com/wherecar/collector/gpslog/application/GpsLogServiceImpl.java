package com.wherecar.collector.gpslog.application;

import com.wherecar.collector.car.domain.Car;
import com.wherecar.collector.gpslog.application.dto.GpsLogRequest;
import com.wherecar.collector.gpslog.domain.GpsLog;
import com.wherecar.collector.gpslog.domain.GpsLogFactory;
import com.wherecar.collector.car.infrastructure.infra.CarReader;
import com.wherecar.collector.gpslog.infrastructure.infra.GpsLogStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GpsLogServiceImpl implements GpsLogService {

    private final CarReader carReader;
    private final GpsLogStore gpsLogStore;
    private final GpsLogFactory gpsLogFactory;

    @Override
    public void receiveGpsLogs(GpsLogRequest gpsLogRequest) {

        Car car = carReader.getCarByMdn(gpsLogRequest.getMdn());
        String bat = gpsLogRequest.getCList().get(0).getBat();
        List<GpsLog> gpsLogList = gpsLogFactory.toGpsLogList(gpsLogRequest);
        gpsLogStore.storeGpsLogs(gpsLogList, car, bat);
    }
}
