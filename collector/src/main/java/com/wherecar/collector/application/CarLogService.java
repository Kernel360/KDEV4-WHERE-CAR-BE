package com.wherecar.collector.application;

import com.wherecar.collector.application.dto.CarLogRequest;

public interface CarLogService {

    void receiveOnLog(CarLogRequest onLogRequest);

    void receiveOffLog(CarLogRequest offLogRequest);

}
