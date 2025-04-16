package com.wherecar.collector.application;

import com.wherecar.collector.domain.CarStatus;
import com.wherecar.collector.domain.GpsLog;

public interface GpsLogSaveService {

    void saveGpsLog(GpsLog gpsLog, CarStatus carStatus);
}
