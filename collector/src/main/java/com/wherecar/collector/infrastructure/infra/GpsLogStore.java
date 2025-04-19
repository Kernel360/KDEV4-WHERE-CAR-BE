package com.wherecar.collector.infrastructure.infra;

import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.GpsLog;

import java.util.List;

public interface GpsLogStore {

    void storeGpsLogs(List<GpsLog> gpsLogList, Car car, String bat);
}
