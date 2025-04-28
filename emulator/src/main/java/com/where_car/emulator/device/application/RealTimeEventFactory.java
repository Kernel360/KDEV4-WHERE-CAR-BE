package com.where_car.emulator.device.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.where_car.emulator.device.application.dto.CarRequest;
import com.where_car.emulator.device.application.dto.CycleInfoRequest;
import com.where_car.emulator.device.application.dto.TrackingRequest;
import com.where_car.emulator.device.domain.DeviceFactory;
import com.where_car.emulator.device.domain.car.CarDevice;
import com.where_car.emulator.device.domain.car.CarIdentity;
import com.where_car.emulator.device.domain.cycle.CarCycleInfo;
import com.where_car.emulator.device.domain.cycle.CycleInfo;
import com.where_car.emulator.device.domain.event.CarStart;
import com.where_car.emulator.device.domain.event.CarStop;
import com.where_car.emulator.device.infrastructure.JsonDatabase;
import com.where_car.emulator.global.constants.DateConstant;
import com.where_car.emulator.global.constants.GpsConstant;
import com.where_car.emulator.global.utill.CoordinateUtils;
import com.where_car.emulator.global.utill.GpsUtils;
import com.where_car.emulator.global.utill.RandomUtils;
import com.where_car.emulator.gps.application.GpsPathService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 차량 이벤트 데이터를 생성하는 컴포넌트
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RealTimeEventFactory {
    
    private final DeviceFactory deviceFactory;
    private final GpsPathService gpsPathService;
    private final JsonDatabase jsonDatabase;
    private final CarIdentity carIdentity;

    private String previousLat = "";
    private String previousLon = "";
    private String startTime = "";
    private Integer totalDistance = 0;

	@PostConstruct
    public void init() {
        initializeTotalDistance();
    }

    public CycleInfoRequest generateCycleInfo(List<TrackingRequest> trackingLocationList) {

        List<CarCycleInfo> carCycleInfoList = trackingLocationList.stream()
            .map(trackingRequest -> {

                String lat = String.valueOf(trackingRequest.getLatitude());
                String lon = String.valueOf(trackingRequest.getLongitude());

                String createTime = trackingRequest.getTimeStamp().format(DateConstant.DATE_TIME_FORMATTER);
                String splitSecond = createTime.substring(createTime.length() - 2);

                return createCarCycleInfo(lat, lon, splitSecond);
            })
            .collect(Collectors.toList());

        String createTime = LocalDateTime.now().format(DateConstant.DATE_TIME_MINUTE_FORMATTER);

        CycleInfo cycleInfo = CycleInfo.builder()
            .mdn(carIdentity.getMdn())
            .carDevice(CarDevice.getDefaultDevice())
            .oTime(createTime)
            .cCnt(String.valueOf(carCycleInfoList.size()))
            .cList(carCycleInfoList)
            .build();

        return deviceFactory.createCycleInfoRequest(cycleInfo);
    }

    public CarRequest generateCarStart(TrackingRequest trackingRequest) {

        startTime = trackingRequest.getTimeStamp().format(DateConstant.DATE_TIME_FORMATTER);
        String splitSecond = startTime.substring(startTime.length() - 2);

        CarCycleInfo carCycleInfo = createStartingCarCycleInfo(trackingRequest, splitSecond);

        CarStart carStart = CarStart.builder()
            .mdn(carIdentity.getMdn())
            .carDevice(CarDevice.getDefaultDevice())
            .onTime(startTime)
            .offTime("")
            .cycleInfo(carCycleInfo)
            .build();

        return deviceFactory.createCarStartRequest(carStart);
    }

    public CarRequest generateCarStop(TrackingRequest trackingRequest) {

        String endTime = trackingRequest.getTimeStamp().format(DateConstant.DATE_TIME_FORMATTER);
        String splitSecond = endTime.substring(endTime.length() - 2);

        CarCycleInfo carCycleInfo = createStartingCarCycleInfo(trackingRequest, splitSecond);

        CarStop carStop = CarStop.builder()
            .mdn(carIdentity.getMdn())
            .carDevice(CarDevice.getDefaultDevice())
            .onTime(startTime)
            .offTime(endTime)
            .cycleInfo(carCycleInfo)
            .build();

        if (StringUtils.hasText(startTime) && StringUtils.hasText(endTime)) {
            startTime = "";
        }

        return deviceFactory.createCarStopRequest(carStop);
    }

    private CarCycleInfo createCarCycleInfo(String curLat, String curLon, String second) {

        String preLat = StringUtils.hasText(previousLat) ? previousLat : curLat;
        String preLon = StringUtils.hasText(previousLon) ? previousLon : curLon;

        int distance = calculateDistance(preLat, preLon, curLat, curLon);
        int angle = calculateAngle(preLat, preLon, curLat, curLon);
        int speed = calculateSpeed(distance);

        previousLat = curLat;
        previousLon = curLon;

        totalDistance += distance;
        saveTotalDistance();

        return CarCycleInfo.builder()
            .sec(second)
            .gcd(GpsConstant.GPS_STATUS_NORMAL)
            .lat(CoordinateUtils.formatCoordinate(curLat))
            .lon(CoordinateUtils.formatCoordinate(curLon))
            .ang(String.valueOf(angle))
            .spd(String.valueOf(speed))
            .sum(String.valueOf(totalDistance))
            .bat(String.valueOf(RandomUtils.generateRandomBatteryValue()))
            .build();
    }

    private CarCycleInfo createStartingCarCycleInfo(TrackingRequest trackingRequest, String second) {

        String curLat = String.valueOf(trackingRequest.getLatitude());
        String curLon = String.valueOf(trackingRequest.getLongitude());
        String preLat = StringUtils.hasText(previousLat) ? previousLat : curLat;
        String preLon = StringUtils.hasText(previousLon) ? previousLon : curLon;

        int distance = calculateDistance(preLat, preLon, curLat, curLon);
        int angle = calculateAngle(preLat, preLon, curLat, curLon);
        int speed = calculateSpeed(distance);

        previousLat = curLat;
        previousLon = curLon;

        totalDistance += distance;
        saveTotalDistance();

        return CarCycleInfo.builder()
            .sec(second)
            .gcd(GpsConstant.GPS_STATUS_NORMAL)
            .lat(CoordinateUtils.formatCoordinate(curLat))
            .lon(CoordinateUtils.formatCoordinate(curLon))
            .ang(String.valueOf(angle))
            .spd(String.valueOf(speed))
            .sum(String.valueOf(totalDistance))
            .bat(String.valueOf(RandomUtils.generateRandomBatteryValue()))
            .build();
    }

    private int calculateDistance(String preLat, String preLon, String curLat, String curLon) {
        double distance = GpsUtils.calculateDistance(
            Double.parseDouble(preLat),
            Double.parseDouble(preLon),
            Double.parseDouble(curLat),
            Double.parseDouble(curLon)
        );

        return (int)Math.ceil(distance);  // 올림하여 정수 반환
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

    public void initializeTotalDistance() {
        jsonDatabase.checkAndUpdateMdnChange();

        Optional<CarIdentity> carIdentityOptional = jsonDatabase.getCarIdentityByMdn(carIdentity.getMdn());
        if (carIdentityOptional.isPresent()) {
            CarIdentity loadedCarIdentity = carIdentityOptional.get();
            totalDistance = Integer.valueOf(loadedCarIdentity.getTotalDistance());
            log.info("DB 누적 주행거리 로드: {}", totalDistance);
        } else {
            log.warn("CarIdentity를 찾을 수 없습니다. MDN: {}", carIdentity.getMdn());
        }
    }

    public void saveTotalDistance() {
        try {
            Integer currentTotalDistance = totalDistance;
            jsonDatabase.getCarIdentityByMdn(carIdentity.getMdn()).ifPresent(identity -> {
                identity.setTotalDistance(String.valueOf(currentTotalDistance));
                jsonDatabase.updateCarIdentity(identity);
                log.info("차량 누적 주행거리 업데이트 완료: {} m", currentTotalDistance);
            });
        } catch (Exception e) {
            log.error("누적 주행거리 저장 중 오류 발생: {}", e.getMessage());
        }
    }
}
