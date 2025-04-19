package com.wherecar.collector.application;

import com.wherecar.collector.application.dto.GpsLogRequest;

public interface GpsLogService {

    void receiveGpsLogs(GpsLogRequest gpsLogRequest);
}
