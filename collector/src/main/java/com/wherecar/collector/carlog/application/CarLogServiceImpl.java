package com.wherecar.collector.carlog.application;

import com.wherecar.collector.car.domain.Car;
import com.wherecar.collector.carlog.domain.CarLog;
import com.wherecar.collector.carlog.application.dto.CarLogRequest;
import com.wherecar.collector.carlog.domain.CarLogFactory;
import com.wherecar.collector.carlog.infrastructure.CarLogReader;
import com.wherecar.collector.carlog.infrastructure.CarLogStore;
import com.wherecar.collector.car.infrastructure.CarReader;
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
    private final CarLogFactory carLogFactory;

    @Override
    public void receiveOnLog(CarLogRequest onLogRequest) {
        log.info("[CARLOG][CarLogServiceImpl][receiveOnLog] 시작 | onLogRequest = {}", onLogRequest);

        Car car = carReader.getCarByMdn(onLogRequest.getMdn());
        Optional<CarLog> optionalPreviousCarLog = carLogReader.findPreviousOffLogByMdn(car.getMdn());

        if (optionalPreviousCarLog.isEmpty()) {
            CarLog carLog = carLogFactory.toFirstOnLog(onLogRequest);
            carLogStore.storeFirstOnLog(car, carLog);
        } else {
            CarLog carLog = carLogFactory.toOnLog(onLogRequest, optionalPreviousCarLog.get().getOffMileage());
            carLogStore.storeOnLog(onLogRequest, car, optionalPreviousCarLog.get(), carLog);
        }
        log.info("[CARLOG][CarLogServiceImpl][receiveOnLog] 끝");
    }

    @Override
    public void receiveOffLog(CarLogRequest offLogRequest) {
        log.info("[CARLOG][CarLogServiceImpl][receiveOffLog] 시작 | offLogRequest = {}", offLogRequest);

        Car car = carReader.getCarByMdn(offLogRequest.getMdn());
        CarLog previousCarLog = carLogReader.getPreviousOnLogByMdn(car.getMdn());
        CarLog carLog = carLogFactory.toOffLog(offLogRequest, previousCarLog);
        carLogStore.storeOffLog(offLogRequest, car, previousCarLog, carLog);
        log.info("[CARLOG][CarLogServiceImpl][receiveOffLog] 끝");
    }

}
