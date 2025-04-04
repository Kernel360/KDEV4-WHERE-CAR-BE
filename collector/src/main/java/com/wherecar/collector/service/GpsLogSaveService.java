package com.wherecar.collector.service;

import com.wherecar.collector.domain.CarStatus;
import com.wherecar.collector.domain.GpsLog;

public interface GpsLogSaveService {

    void saveGpsLog(GpsLog gpsLog, CarStatus carStatus);
}
