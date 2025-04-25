package com.where_car.emulator.device.application;

import org.springframework.stereotype.Service;

import com.where_car.emulator.device.application.dto.LocationRequest;
import com.where_car.emulator.device.domain.car.CarIdentity;
import com.where_car.emulator.device.domain.device.DeviceEntity;
import com.where_car.emulator.global.error.DeviceErrorCode;
import com.where_car.emulator.global.error.DeviceException;
import com.where_car.emulator.global.utill.FileUtils;
import com.where_car.emulator.gps.application.GpsPathService;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

  @Getter
  private final CarIdentity carIdentity;
  private final DeviceScheduler schedulerService;
  private final DeviceInfoService deviceInfoService;
  private final DeviceEventFactory deviceEventFactory;
  private final GpsPathService gpsPathService;
  private final DeviceEntity deviceEntity;

  @PostConstruct
  public void init() {
    log.info("에뮬레이터의 주행 경로를 선택했습니다: {}", gpsPathService.getSelectGpxFile().getFilename());
  }

  public void toggleDevice() {
    if (!deviceEntity.isOn()) {
      try {
        deviceEntity.turnOn();
        startDevice();
      } catch (Exception e) {
        deviceEntity.turnOff(); // 상태를 원래대��� 되돌림
        log.error("차량 시동 과정에서 오류 발생: {}", e.getMessage());
        throw new DeviceException(DeviceErrorCode.DEVICE_START_FAILED, e);
      }
    } else {
      try {
        log.info("차량의 Key-Off 신호가 감지되었습니다, 스케줄러를 중지합니다.");
        deviceEntity.turnOff();
        stopDevice();
      } catch (Exception e) {
        deviceEntity.turnOn(); // 상태를 원래대로 되돌림
        log.error("차량 시동 종료 과정에서 오류 발생: {}", e.getMessage());
        throw new DeviceException(DeviceErrorCode.DEVICE_STOP_FAILED, e);
      }
    }
  }

  private void startDevice() {
    deviceInfoService.generateAndSendCarStart();
    schedulerService.startScheduler(this::handleScheduledTask);
  }

  private void stopDevice() {
    schedulerService.stopScheduler();
    deviceInfoService.generateAndSendCycleInfo();
    deviceInfoService.generateAndSendCarStop();
    deviceEventFactory.saveTotalDistance();
    deviceEventFactory.saveGpsIndex();
  }

  private void handleScheduledTask() {
    deviceInfoService.generateAndSendCarCycleInfo();

    if (deviceInfoService.getCarCycleInfoList().size() >= 60) {
      deviceInfoService.generateAndSendCycleInfo();
    }
  }

  private LocationRequest extractLocationInfoFromFilename(String fileName) {
    String departure = "기본 출발지";
    String destination = "기본 도착지";

    try {
      if (fileName != null && !fileName.trim().isEmpty()) {
        String[] locations = FileUtils.extractLocations(fileName);
        if (locations.length == 2) {
          if (isValidLocationName(locations[0])) {
            departure = locations[0];
          }
          if (isValidLocationName(locations[1])) {
            destination = locations[1];
          }
        }
      }
    } catch (Exception e) {
        log.error("파일 이름에서 위치 정보를 추출하는 중 오류 발생: {}", e.getMessage());
    }

    return new LocationRequest(departure, destination);
  }

  public LocationRequest getLocationInfo() {
    String fileName = gpsPathService.getSelectGpxFile().getFilename();
    return extractLocationInfoFromFilename(fileName);
  }

  private boolean isValidLocationName(String location) {
    return location != null && !location.trim().isEmpty();
  }

  public boolean getDeviceStatus() {
    return deviceEntity.isOn();
  }
}
