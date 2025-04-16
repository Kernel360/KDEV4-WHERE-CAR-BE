package com.wherecar.collector.application;

import com.wherecar.collector.application.dto.CarLogRequest;
import com.wherecar.collector.application.dto.CarLogResponse;

public interface CarLogConverterService {

    CarLogResponse receiveOnLog(CarLogRequest onLogRequest);

    CarLogResponse receiveOffLog(CarLogRequest offLogRequest);

}
