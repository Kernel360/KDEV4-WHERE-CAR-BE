package com.wherecar.collector.infrastructure.infra;

import com.wherecar.collector.application.dto.CarLogRequest;
import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.CarLog;

public interface CarLogStore {

    void storeFirstOnLog(Car car, CarLog carLog);

    void storeOnLog(CarLogRequest onLogRequest, Car car, CarLog previousCarLog, CarLog carLog);

    void storeOffLog(CarLogRequest offLogRequest, Car car, CarLog previousCarLog, CarLog carLog);
}
