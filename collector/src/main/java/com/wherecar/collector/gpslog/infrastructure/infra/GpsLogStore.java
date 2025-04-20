package com.wherecar.collector.gpslog.infrastructure.infra;

import com.wherecar.collector.car.domain.Car;
import com.wherecar.collector.gpslog.domain.GpsLog;

import java.util.List;

public interface GpsLogStore {

    void storeGpsLogs(List<GpsLog> gpsLogList, Car car, String bat);
}
