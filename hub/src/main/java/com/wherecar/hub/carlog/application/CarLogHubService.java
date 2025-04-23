package com.wherecar.hub.carlog.application;

import com.wherecar.hub.carlog.application.dto.CarLogRequest;
import com.wherecar.hub.common.application.dto.MessageResponse;

public interface CarLogHubService {
    MessageResponse sendCarOnLogMessage(CarLogRequest onLogRequest);

    MessageResponse sendCarOffLogMessage(CarLogRequest offLogRequest);
}
