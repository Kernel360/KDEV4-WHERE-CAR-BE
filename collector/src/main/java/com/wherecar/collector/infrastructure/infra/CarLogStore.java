package com.wherecar.collector.infrastructure.infra;

import com.wherecar.collector.application.dto.CarLogRequest;
import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.CarLog;

public interface CarLogStore {

    void storeFirstOnLog(CarLogRequest onLogRequest, Car car);

    void storeOnLog(CarLogRequest onLogRequest, Car car, CarLog previousCarLog);

    void storeOffLog(CarLogRequest offLogRequest, Car car, CarLog previousCarLog);
}
