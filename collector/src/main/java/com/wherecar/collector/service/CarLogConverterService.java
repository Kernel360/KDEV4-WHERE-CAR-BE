package com.wherecar.collector.service;

import com.wherecar.collector.dto.CarLogRequest;

public interface CarLogConverterService {

    void receiveOnLog(CarLogRequest onLogRequest);

    void receiveOffLog(CarLogRequest offLogRequest);

}
