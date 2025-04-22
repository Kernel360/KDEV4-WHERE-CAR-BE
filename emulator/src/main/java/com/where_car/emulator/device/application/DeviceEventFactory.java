package com.where_car.emulator.device.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import com.where_car.emulator.device.application.dto.CarDto;
import com.where_car.emulator.device.application.dto.CycleInfoDto;
import com.where_car.emulator.device.domain.DeviceFactory;
import com.where_car.emulator.device.domain.car.CarDevice;
import com.where_car.emulator.device.domain.car.CarIdentity;
import com.where_car.emulator.device.domain.cycle.CarCycleInfo;
import com.where_car.emulator.device.domain.cycle.CycleInfo;
import com.where_car.emulator.device.domain.event.CarStart;
import com.where_car.emulator.device.domain.event.CarStop;
import com.where_car.emulator.global.constants.DateConstant;
import com.where_car.emulator.global.utill.GpsUtils;
import com.where_car.emulator.global.utill.RandomUtils;
import com.where_car.emulator.global.utill.StringUtils;
import com.where_car.emulator.gps.application.GpsPathService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 차량 이벤트 데이터를 생성하는 컴포넌트
 */
@Component
@RequiredArgsConstructor
public class DeviceEventFactory {
    
    private final DeviceFactory deviceFactory;
    private final GpsPathService gpsPathService;
    
    @Getter
    private String startTime = "";
    
    @Getter
    private Integer totalDistance = 0;

    public CarDto generateCarStart(CarIdentity carIdentity, Resource gpxFile, Integer initialDistance) {
        List<Element> firstTrkpt = gpsPathService.getFirstTrkpt(gpxFile);
        totalDistance = initialDistance;
        
        CarCycleInfo carCycleInfo = createCycleInfoFromTrkpt(firstTrkpt, totalDistance, "00", "0");

        startTime = LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER);
        
        CarStart carStart = CarStart.builder()
            .carIdentity(carIdentity)
            .carDevice(CarDevice.builder().build())
            .onTime(startTime)
            .offTime("")
            .cycleInfo(carCycleInfo)
            .build();

        return deviceFactory.createCarStartDto(carStart);
    }

    public CarCycleInfo generateCarCycleInfo(List<CarCycleInfo> carCycleInfoList, 
                                           Resource gpxFile, 
                                           int gpsListCount,
                                           Integer currentTotalDistance) {
        totalDistance = currentTotalDistance;
        String previousSecond = calculatePreviousSecond(carCycleInfoList);
        int batteryValue = RandomUtils.generateRandomBatteryValue();

        List<Element> allTrkpts = gpsPathService.getAllTrkpts(gpxFile);
        
        return createCarCycleInfo(allTrkpts, gpsListCount, previousSecond, batteryValue);
    }
    
    private CarCycleInfo createCarCycleInfo(List<Element> allTrkpts, int gpsListCount, String previousSecond, int batteryValue) {

        String curLat = allTrkpts.get(gpsListCount).getAttribute("lat");
        String curLon = allTrkpts.get(gpsListCount).getAttribute("lon");
        String preLat = allTrkpts.get(gpsListCount != 0 ? gpsListCount - 1 : gpsListCount).getAttribute("lat");
        String preLon = allTrkpts.get(gpsListCount != 0 ? gpsListCount - 1 : gpsListCount).getAttribute("lon");

        double distance = calculateDistance(preLat, preLon, curLat, curLon);
        int angle = calculateAngle(preLat, preLon, curLat, curLon);
        int speed = calculateSpeed(distance);

        return CarCycleInfo.builder()
            .sec(previousSecond)
            .gcd("A")
            .lat(StringUtils.formatCoordinate(curLat))
            .lon(StringUtils.formatCoordinate(curLon))
            .ang(String.valueOf(angle))
            .spd(String.valueOf(speed))
            .sum(String.valueOf(totalDistance))
            .bat(String.valueOf(batteryValue))
            .build();
    }

    public CycleInfoDto generateCycleInfo(CarIdentity carIdentity, List<CarCycleInfo> carCycleInfoList) {
        CycleInfo cycleInfo = CycleInfo.builder()
            .carIdentity(carIdentity)
            .carDevice(CarDevice.builder().build())
            .oTime(LocalDateTime.now().format(DateConstant.DATE_TIME_MINUTE_FORMATTER))
            .cCnt(String.valueOf(carCycleInfoList.size()))
            .cList(carCycleInfoList)
            .build();

        return deviceFactory.createCycleInfoDto(cycleInfo);
    }

    public CarDto generateCarStop(CarIdentity carIdentity, Resource gpxFile, String startTime, Integer currentTotalDistance) {
        totalDistance = currentTotalDistance;
        List<Element> lastTrkpt = gpsPathService.getLastTrkpt(gpxFile);

        CarCycleInfo carCycleInfo = createCycleInfoFromTrkpt(lastTrkpt, totalDistance, "00", "0");

        CarStop carStop = CarStop.builder()
            .carIdentity(carIdentity)
            .carDevice(CarDevice.builder().build())
            .onTime(startTime)
            .offTime(LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER))
            .cycleInfo(carCycleInfo)
            .build();

        return deviceFactory.createCarStopDto(carStop);
    }

    private CarCycleInfo createCycleInfoFromTrkpt(List<Element> trkpt, Integer distance, String second, String battery) {
        String latitude = StringUtils.formatCoordinate(trkpt.get(0).getAttribute("lat"));
        String longitude = StringUtils.formatCoordinate(trkpt.get(0).getAttribute("lon"));
        String angle = StringUtils.calculateAngleFromCoordinates(trkpt);
        String speed = StringUtils.calculateSpeedFromCoordinates(trkpt);

        return CarCycleInfo.builder()
            .sec(second)
            .gcd("A")
            .lat(latitude)
            .lon(longitude)
            .ang(angle)
            .spd(speed)
            .sum(String.valueOf(distance))
            .bat(battery)
            .build();
    }

    private String calculatePreviousSecond(List<CarCycleInfo> carCycleInfoList) {
        return carCycleInfoList.isEmpty() ?
            "00" :
            String.format("%02d", (Integer.parseInt(carCycleInfoList.get(carCycleInfoList.size() - 1).getSec()) + 1) % 60);
    }

    private double calculateDistance(String preLat, String preLon, String curLat, String curLon) {
        double distance = GpsUtils.calculateDistance(
            Double.parseDouble(preLat),
            Double.parseDouble(preLon),
            Double.parseDouble(curLat),
            Double.parseDouble(curLon)
        );

        totalDistance += (int) Math.round(distance);
        return distance;
    }

    private int calculateAngle(String preLat, String preLon, String curLat, String curLon) {
        return GpsUtils.calculateBearing(
            Double.parseDouble(preLat),
            Double.parseDouble(preLon),
            Double.parseDouble(curLat),
            Double.parseDouble(curLon)
        );
    }

    private int calculateSpeed(double distance) {
        return (int) Math.round(GpsUtils.calculateSpeed(distance, 1));
    }
}
