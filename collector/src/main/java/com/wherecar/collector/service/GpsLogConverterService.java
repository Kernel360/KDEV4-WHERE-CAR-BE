package com.wherecar.collector.service;

import com.wherecar.collector.dto.GpsLogRequest;

public interface GpsLogConverterService {

    void receiveGpsLog(GpsLogRequest gpsLogRequest);
}
