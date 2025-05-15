package com.wherecar.collector.carlog.domain;

import com.wherecar.collector.carlog.application.dto.CarLogRequest;
import com.wherecar.collector.common.constant.DriveType;
import com.wherecar.collector.common.constant.GpsConditionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class CarLogFactory {

    public CarLog toOnLog(CarLogRequest carLogRequest, Double previousOffMileage) {

        Double onMileage = previousOffMileage;  // 시동 ON 시 mileage는 직전 시동 OFF 시 mileage
        Double doubleLatitude = CarLog.parseLatLon(carLogRequest.getLat());
        Double doubleLongitude = CarLog.parseLatLon(carLogRequest.getLon());
        LocalDateTime onTime = CarLog.parseOnOffTime(carLogRequest.getOnTime());
        GpsConditionType onGpsCondition = CarLog.getGpsConditionType(carLogRequest.getGcd());

        return CarLog.builder()
                .driveType(DriveType.UNCLASSIFIED)
                .mdn(carLogRequest.getMdn())
                .onGpsCondition(onGpsCondition)
                .onLatitude(doubleLatitude)
                .onLongitude(doubleLongitude)
                .onAngle(Integer.parseInt(carLogRequest.getAng()))
                .onSpeed(Integer.parseInt(carLogRequest.getSpd()))
                .onSum(Integer.parseInt(carLogRequest.getSum()))
                .onMileage(onMileage)
                .onTime(onTime)
                .build();
    }

    public CarLog toFirstOnLog(CarLogRequest carLogRequest) {

        Integer onSum = 0;
        Double onMileage = 0.0;

        Double doubleLatitude = CarLog.parseLatLon(carLogRequest.getLat());
        Double doubleLongitude = CarLog.parseLatLon(carLogRequest.getLon());
        LocalDateTime onTime = CarLog.parseOnOffTime(carLogRequest.getOnTime());
        GpsConditionType onGpsCondition = CarLog.getGpsConditionType(carLogRequest.getGcd());

        return CarLog.builder()
                .driveType(DriveType.UNCLASSIFIED)
                .mdn(carLogRequest.getMdn())
                .onGpsCondition(onGpsCondition)
                .onLatitude(doubleLatitude)
                .onLongitude(doubleLongitude)
                .onAngle(Integer.parseInt(carLogRequest.getAng()))
                .onSpeed(Integer.parseInt(carLogRequest.getSpd()))
                .onSum(onSum)
                .onMileage(onMileage)
                .onTime(onTime)
                .build();
    }

}
