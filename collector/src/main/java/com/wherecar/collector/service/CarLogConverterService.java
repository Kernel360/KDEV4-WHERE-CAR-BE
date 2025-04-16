package com.wherecar.collector.service;

import com.wherecar.collector.dto.CarLogRequest;
import com.wherecar.collector.dto.CarLogResponse;

public interface CarLogConverterService {

    CarLogResponse receiveOnLog(CarLogRequest onLogRequest);

    CarLogResponse receiveOffLog(CarLogRequest offLogRequest);

}
