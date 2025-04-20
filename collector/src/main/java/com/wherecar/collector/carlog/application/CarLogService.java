package com.wherecar.collector.carlog.application;

import com.wherecar.collector.carlog.application.dto.CarLogRequest;

public interface CarLogService {

    void receiveOnLog(CarLogRequest onLogRequest);

    void receiveOffLog(CarLogRequest offLogRequest);

}
