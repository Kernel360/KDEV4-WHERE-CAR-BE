package com.wherecar.hub.gpslog.application;

import com.wherecar.hub.gpslog.application.dto.GpsLogRequest;

public interface GpsLogHubService {
    void sendGpsLogMessage(GpsLogRequest gpsLogRequest);
}
