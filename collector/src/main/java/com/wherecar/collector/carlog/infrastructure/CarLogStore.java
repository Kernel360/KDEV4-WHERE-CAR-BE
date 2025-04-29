package com.wherecar.collector.carlog.infrastructure;

import com.wherecar.collector.carlog.application.dto.CarLogRequest;
import com.wherecar.collector.car.domain.Car;
import com.wherecar.collector.carlog.domain.CarLog;

public interface CarLogStore {

    void storeFirstOnLog(Car car, CarLog carLog);

    void storeOnLog(CarLogRequest onLogRequest, Car car, CarLog previousCarLog, CarLog carLog);

    void storeOffLog(CarLogRequest offLogRequest, Car car, CarLog previousCarLog);
}
