package com.wherecar.collector.infrastructure.infra;

import com.wherecar.collector.application.dto.CarLogRequest;
import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.CarLog;
import com.wherecar.collector.domain.CarLogFactory;
import com.wherecar.collector.domain.CarStatus;
import com.wherecar.collector.domain.constant.CarState;
import com.wherecar.collector.infrastructure.CarLogRepository;
import com.wherecar.collector.infrastructure.CarStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarLogStoreImpl implements CarLogStore {

    private final CarLogFactory carLogFactory;
    private final CarLogRepository carLogRepository;
    private final CarStatusRepository carStatusRepository;

    @Override
    public void storeFirstOnLog(CarLogRequest onLogRequest, Car car) {

        Integer onSum = 0;
        Double onMileage = 0.0; // 최초 출고일 땐 mileage가 0

        CarLog carLog = carLogFactory.toOnLog(onLogRequest, onSum, onMileage);  // onSum == 0, onMileage == 0.0
        carLogRepository.save(carLog);

        CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
        carStatus.changeMileage(onMileage);
        carStatus.changeCarState(CarState.RUNNING);
        carStatusRepository.save(carStatus);
    }

    @Override
    public void storeOnLog(CarLogRequest onLogRequest, Car car, CarLog previousCarLog) {

        // 시동 ON 시 최초 누적 거리 != 직전 시동 OFF일 때의 누적 거리
        if (!CarLog.isSameOffSum(previousCarLog.getOffSum(), onLogRequest.getSum())) {
            throw new RuntimeException("(시동 ON 시 최초 누적 거리) != (직전 시동 OFF일 때의 누적 거리)");

        } else { // 시동 ON 시 최초 누적 거리 == 직전 시동 OFF일 때의 누적 거리
            CarLog carLog = carLogFactory.toOnLog(onLogRequest, previousCarLog);
            carLogRepository.save(carLog);

            CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
            carStatus.changeMileage(previousCarLog.getOffMileage());
            carStatus.changeCarState(CarState.RUNNING);
            carStatusRepository.save(carStatus);
        }
    }

    @Override
    public void storeOffLog(CarLogRequest offLogRequest, Car car, CarLog previousCarLog) {
        CarLog carLog = carLogFactory.toOffLog(offLogRequest, previousCarLog);
        carLogRepository.save(carLog);

        Integer sumToAdd = CarLog.getSumToAdd(previousCarLog.getOnSum(), Integer.parseInt(offLogRequest.getSum()));
        Double offMileage = CarLog.getOffMileage(previousCarLog.getOnMileage(), sumToAdd);

        CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
        carStatus.changeMileage(offMileage);
        carStatus.changeCarState(CarState.STOPPED);
        carStatusRepository.save(carStatus);
    }

}
