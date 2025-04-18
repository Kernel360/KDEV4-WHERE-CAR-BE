package com.wherecar.collector.domain;

import com.wherecar.collector.application.dto.CarLogRequest;
import com.wherecar.collector.domain.constant.GpsConditionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class CarLogFactory {

    public CarLog toOnLog(CarLogRequest carLogRequest, CarLog previousCarLog) {

        Double onMileage = previousCarLog.getOffMileage();  // 시동 ON 시 mileage는 직전 시동 OFF 시 mileage
        Double doubleLatitude = CarLog.parseLatLon(carLogRequest.getLat());
        Double doubleLongitude = CarLog.parseLatLon(carLogRequest.getLon());
        LocalDateTime onTime = CarLog.parseOnOffTime(carLogRequest.getOnTime());
        GpsConditionType onGpsCondition = CarLog.getGpsConditionType(carLogRequest.getGcd());

        return CarLog.builder()
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

    public CarLog toOnLog(CarLogRequest carLogRequest, Integer onSum, Double onMileage) {

        Double doubleLatitude = CarLog.parseLatLon(carLogRequest.getLat());
        Double doubleLongitude = CarLog.parseLatLon(carLogRequest.getLon());
        LocalDateTime onTime = CarLog.parseOnOffTime(carLogRequest.getOnTime());
        GpsConditionType onGpsCondition = CarLog.getGpsConditionType(carLogRequest.getGcd());

        return CarLog.builder()
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
    
    public CarLog toOffLog(CarLogRequest carLogRequest, CarLog previousCarLog) {
        
        Integer onSum = previousCarLog.getOnSum();    // 직전 ON 로그의 sum
        Integer offSum = Integer.parseInt(carLogRequest.getSum());      // OFF 로그의 sum

        Integer sumToAdd = CarLog.getSumToAdd(onSum, offSum);
        Double offMileage = CarLog.getOffMileage(previousCarLog.getOnMileage(), sumToAdd);

        Double doubleLatitude = CarLog.parseLatLon(carLogRequest.getLat());
        Double doubleLongitude = CarLog.parseLatLon(carLogRequest.getLon());

        LocalDateTime onTime = CarLog.parseOnOffTime(carLogRequest.getOnTime());
        LocalDateTime offTime = CarLog.parseOnOffTime(carLogRequest.getOffTime());

        GpsConditionType offGpsCondition = CarLog.getGpsConditionType(carLogRequest.getGcd());

        return CarLog.builder()
                .mdn(carLogRequest.getMdn())
                .onGpsCondition(previousCarLog.getOnGpsCondition())
                .onLatitude(previousCarLog.getOnLatitude())
                .onLongitude(previousCarLog.getOnLongitude())
                .onAngle(previousCarLog.getOnAngle())
                .onSpeed(previousCarLog.getOnSpeed())
                .onSum(onSum)
                .onMileage(previousCarLog.getOnMileage())
                .onTime(onTime)
                .offGpsCondition(offGpsCondition)
                .offLatitude(doubleLatitude)
                .offLongitude(doubleLongitude)
                .offAngle(Integer.parseInt(carLogRequest.getAng()))
                .offSpeed(Integer.parseInt(carLogRequest.getSpd()))
                .offSum(Integer.parseInt(carLogRequest.getSum()))
                .offMileage(offMileage)
                .offTime(offTime)
                .build();
    }


}
