package com.wherecar.hub.application;

import com.wherecar.hub.application.dto.GpsLogRequest;
import com.wherecar.hub.application.dto.MessageResponse;

public interface GpsLogHubService {
    MessageResponse sendGpsLogMessage(GpsLogRequest gpsLogRequest);
}
