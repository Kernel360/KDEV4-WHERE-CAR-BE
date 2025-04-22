package com.wherecar.collector.gpslog.application;

import com.wherecar.collector.gpslog.application.dto.GpsLogRequest;

public interface GpsLogService {

    void receiveGpsLogs(GpsLogRequest gpsLogRequest);
}
