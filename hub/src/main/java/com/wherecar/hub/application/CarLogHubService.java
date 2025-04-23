package com.wherecar.hub.application;

import com.wherecar.hub.application.dto.CarLogRequest;
import com.wherecar.hub.application.dto.MessageResponse;

public interface CarLogHubService {
    MessageResponse sendCarOnLogMessage(CarLogRequest onLogRequest);

    MessageResponse sendCarOffLogMessage(CarLogRequest offLogRequest);
}
