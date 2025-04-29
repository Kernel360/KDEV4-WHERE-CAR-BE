package com.wherecar.collector.carlog.infrastructure;

import com.wherecar.collector.carlog.application.dto.CarLogRequest;
import com.wherecar.collector.car.domain.Car;
import com.wherecar.collector.carlog.domain.CarLog;
import com.wherecar.collector.common.constant.CarState;
import com.wherecar.collector.car.infrastructure.CarStatusRepository;
import com.wherecar.collector.common.constant.GpsConditionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarLogStoreImpl implements CarLogStore {

    private final CarLogRepository carLogRepository;
    private final CarStatusRepository carStatusRepository;

    @Override
    public void storeFirstOnLog(Car car, CarLog carLog) {

        carLogRepository.save(carLog);

        carStatusRepository.updateMileage(car.getId(), 0.0);
        carStatusRepository.updateCarState(car.getId(), CarState.RUNNING);
    }

    @Override
    public void storeOnLog(CarLogRequest onLogRequest, Car car, CarLog previousCarLog, CarLog carLog) {

//        // 시동 ON 시 최초 누적 거리 != 직전 시동 OFF일 때의 누적 거리
//        if (!CarLog.isSameOffSum(previousCarLog.getOffSum(), onLogRequest.getSum())) {
//            log.error("(시동 ON 시 최초 누적 거리) != (직전 시동 OFF일 때의 누적 거리)");
//
//        } else { // 시동 ON 시 최초 누적 거리 == 직전 시동 OFF일 때의 누적 거리
//            carLogRepository.save(carLog);
//
//            carStatusRepository.updateCarState(car.getId(), CarState.RUNNING);
//        }

        carLogRepository.save(carLog);

        carStatusRepository.updateCarState(car.getId(), CarState.RUNNING);
    }

    @Override
    public void storeOffLog(CarLogRequest offLogRequest, Car car, CarLog previousCarLog) {

        Integer onSum = previousCarLog.getOnSum();    // 직전 ON 로그의 sum
        Integer offSum = Integer.parseInt(offLogRequest.getSum());      // OFF 로그의 sum

        Integer sumToAdd = CarLog.getSumToAdd(onSum, offSum);
        Double offMileage = CarLog.getOffMileage(previousCarLog.getOnMileage(), sumToAdd);

        Double doubleLatitude = CarLog.parseLatLon(offLogRequest.getLat());
        Double doubleLongitude = CarLog.parseLatLon(offLogRequest.getLon());

        LocalDateTime offTime = CarLog.parseOnOffTime(offLogRequest.getOffTime());

        GpsConditionType offGpsCondition = CarLog.getGpsConditionType(offLogRequest.getGcd());

        previousCarLog.setOffGpsCondition(offGpsCondition);
        previousCarLog.setOffLatitude(doubleLatitude);
        previousCarLog.setOffLongitude(doubleLongitude);
        previousCarLog.setOffAngle(Integer.parseInt(offLogRequest.getAng()));
        previousCarLog.setOffSpeed(Integer.parseInt(offLogRequest.getSpd()));
        previousCarLog.setOffSum(Integer.parseInt(offLogRequest.getSum()));
        previousCarLog.setOffMileage(offMileage);
        previousCarLog.setOffTime(offTime);

        carLogRepository.save(previousCarLog);

        carStatusRepository.updateMileage(car.getId(), offMileage);
        carStatusRepository.updateCarState(car.getId(), CarState.STOPPED);
    }

}
