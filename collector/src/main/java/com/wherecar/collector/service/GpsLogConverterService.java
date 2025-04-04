package com.wherecar.collector.service;

import com.wherecar.collector.dto.GpsLogRequest;
import com.wherecar.collector.dto.GpsLogResponse;

public interface GpsLogConverterService {

    GpsLogResponse receiveGpsLog(GpsLogRequest gpsLogRequest);
}
