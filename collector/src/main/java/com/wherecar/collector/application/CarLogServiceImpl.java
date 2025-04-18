package com.wherecar.collector.application;

import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.CarLog;
import com.wherecar.collector.application.dto.CarLogRequest;
import com.wherecar.collector.infrastructure.infra.CarLogReader;
import com.wherecar.collector.infrastructure.infra.CarLogStore;
import com.wherecar.collector.infrastructure.infra.CarReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CarLogServiceImpl implements CarLogService {

    private final CarReader carReader;
    private final CarLogReader carLogReader;
    private final CarLogStore carLogStore;

    @Override
    public void receiveOnLog(CarLogRequest onLogRequest) {

        Car car = carReader.getCarByMdn(onLogRequest.getMdn());
        Optional<CarLog> optionalPreviousCarLog = carLogReader.findPreviousOffLogByMdn(car.getMdn());

        if (optionalPreviousCarLog.isEmpty()) {
            carLogStore.storeFirstOnLog(onLogRequest, car);
        } else {
            carLogStore.storeOnLog(onLogRequest, car, optionalPreviousCarLog.get());
        }
    }

    @Override
    public void receiveOffLog(CarLogRequest offLogRequest) {

        Car car = carReader.getCarByMdn(offLogRequest.getMdn());
        CarLog previousCarLog = carLogReader.getPreviousOnLogByMdn(car.getMdn());
        carLogStore.storeOffLog(offLogRequest, car, previousCarLog);
    }

}
