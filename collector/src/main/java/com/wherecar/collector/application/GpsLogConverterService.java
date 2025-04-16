package com.wherecar.collector.application;

import com.wherecar.collector.application.dto.GpsLogRequest;
import com.wherecar.collector.application.dto.GpsLogResponse;

public interface GpsLogConverterService {

    GpsLogResponse receiveGpsLog(GpsLogRequest gpsLogRequest);
}
