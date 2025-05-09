package com.wherecar.hub.gpslog.application;

import com.wherecar.hub.gpslog.application.dto.GpsLogRequest;
import com.wherecar.hub.common.application.dto.MessageResponse;

public interface GpsLogHubService {
    MessageResponse sendGpsLogMessage(GpsLogRequest gpsLogRequest);
}
