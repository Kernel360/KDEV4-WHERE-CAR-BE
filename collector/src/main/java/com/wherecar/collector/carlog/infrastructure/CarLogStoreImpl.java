package com.wherecar.collector.carlog.infrastructure;

import com.wherecar.collector.carlog.application.dto.CarLogRequest;
import com.wherecar.collector.car.domain.Car;
import com.wherecar.collector.carlog.domain.CarLog;
import com.wherecar.collector.car.domain.CarStatus;
import com.wherecar.collector.common.constant.CarState;
import com.wherecar.collector.car.infrastructure.CarStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarLogStoreImpl implements CarLogStore {

    private final CarLogRepository carLogRepository;
    private final CarStatusRepository carStatusRepository;

    @Override
    public void storeFirstOnLog(Car car, CarLog carLog) {

        carLogRepository.save(carLog);

//        CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
//        CarStatus carStatus = carStatusRepository.findByCarIdForUpdate(car.getId())
//                .orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
//        carStatus.changeMileage(0.0); // 최초 출고일 땐 mileage가 0.0
//        carStatus.changeCarState(CarState.RUNNING);
//        carStatusRepository.save(carStatus);

        carStatusRepository.updateMileage(car.getId(), 0.0);
        carStatusRepository.updateCarState(car.getId(), CarState.RUNNING);
    }

    @Override
    public void storeOnLog(CarLogRequest onLogRequest, Car car, CarLog previousCarLog, CarLog carLog) {

        // 시동 ON 시 최초 누적 거리 != 직전 시동 OFF일 때의 누적 거리
        if (!CarLog.isSameOffSum(previousCarLog.getOffSum(), onLogRequest.getSum())) {
            throw new RuntimeException("(시동 ON 시 최초 누적 거리) != (직전 시동 OFF일 때의 누적 거리)");

        } else { // 시동 ON 시 최초 누적 거리 == 직전 시동 OFF일 때의 누적 거리
            carLogRepository.save(carLog);

//            CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
//            CarStatus carStatus = carStatusRepository.findByCarIdForUpdate(car.getId())
//                    .orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
//            carStatus.changeCarState(CarState.RUNNING);
//            carStatusRepository.save(carStatus);
            carStatusRepository.updateCarState(car.getId(), CarState.RUNNING);
        }
    }

    @Override
    public void storeOffLog(CarLogRequest offLogRequest, Car car, CarLog previousCarLog, CarLog carLog) {

        carLogRepository.save(carLog);

        Integer sumToAdd = CarLog.getSumToAdd(previousCarLog.getOnSum(), Integer.parseInt(offLogRequest.getSum()));
        Double offMileage = CarLog.getOffMileage(previousCarLog.getOnMileage(), sumToAdd);

//        CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
//        CarStatus carStatus = carStatusRepository.findByCarIdForUpdate(car.getId())
//                .orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
//        carStatus.changeMileage(offMileage);
//        carStatus.changeCarState(CarState.STOPPED);
//        carStatusRepository.save(carStatus);

        carStatusRepository.updateMileage(car.getId(), offMileage);
        carStatusRepository.updateCarState(car.getId(), CarState.STOPPED);
    }

}
