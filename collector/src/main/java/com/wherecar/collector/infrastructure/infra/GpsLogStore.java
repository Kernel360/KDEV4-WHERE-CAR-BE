package com.wherecar.collector.infrastructure.infra;

import com.wherecar.collector.application.dto.GpsLogRequest;
import com.wherecar.collector.domain.Car;

public interface GpsLogStore {

    void storeGpsLogs(GpsLogRequest gpsLogRequest, Car car);
}
