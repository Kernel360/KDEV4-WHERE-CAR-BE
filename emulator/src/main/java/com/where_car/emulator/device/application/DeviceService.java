package com.where_car.emulator.device.application;

import org.springframework.stereotype.Service;

import com.where_car.emulator.device.application.dto.LocationDto;
import com.where_car.emulator.device.domain.car.CarIdentity;
import com.where_car.emulator.device.domain.device.DeviceEntity;
import com.where_car.emulator.global.error.DeviceErrorCode;
import com.where_car.emulator.global.error.DeviceException;
import com.where_car.emulator.global.utill.FileUtils;
import com.where_car.emulator.gps.application.GpsPathService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *   디바이스 서비스 클래스
 *   에뮬레이터의 상태를 관리하고 주기적으로 데이터를 생성 및 전송합니다.
 * </pre>
 *
 * @since 2025-03-30
 * @version 1.0
 */
@Slf4j
@Service
public class DeviceService {

  private final DeviceScheduler schedulerService;
  private final DeviceInfoService deviceInfoService;
  private final GpsPathService gpsPathService;
  private final CarIdentity carIdentity;
  private final DeviceEntity deviceEntity;

  public DeviceService(
      DeviceScheduler schedulerService,
      DeviceInfoService deviceInfoService,
      GpsPathService gpsPathService,
      CarIdentity carIdentity,
      DeviceEntity deviceEntity) {
    this.schedulerService = schedulerService;
    this.deviceInfoService = deviceInfoService;
    this.gpsPathService = gpsPathService;
    this.carIdentity = carIdentity;
    this.deviceEntity = deviceEntity;
  }

  @PostConstruct
  public void init() {
    log.info("에뮬레이터의 주행 경로를 선택했습니다: {}", gpsPathService.getRandomGpxFile().getFilename());
  }

  /**
   *  에뮬레이터 상태 변경
   *  Key-On: 시동 ON 정보 전송 -> 1초 마다 차량 주기 정보 생성 -> 1분 마다 전체 차량 주기 정보 생성 ->
   *  Key-Off: 정보 생성
   */
  public void toggleDevice() {
    if (!deviceEntity.isOn()) {
      try {
        log.info("차량의 Key-On 신호가 감지되었습니다, 스케줄러를 작동합니다.");
        deviceEntity.turnOn();
        startDevice();
      } catch (Exception e) {
        deviceEntity.turnOff(); // 상태를 원래대로 되돌림
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
    // 차량 시동 ON 이벤트 생성 및 전송
    deviceInfoService.generateAndSendCarStart();
    // 스케줄러 시작
    schedulerService.startScheduler(this::handleScheduledTask);
  }
  
  private void stopDevice() {
    // 스케줄러 중지
    schedulerService.stopScheduler();
    // 남은 데이터 처리
    deviceInfoService.generateAndSendCycleInfo();
    // 차량 시동 OFF 이벤트 생성 및 전송
    deviceInfoService.generateAndSendCarStop();
  }
  
  private void handleScheduledTask() {
    // 차량 주기 정보 생성
    deviceInfoService.generateCarCycleInfo();
    
    // 60개의 주기 정보가 쌓이면 서버로 전송
    if (deviceInfoService.getCycleInfoListSize() >= 60) {
      deviceInfoService.generateAndSendCycleInfo();
    }
  }

  public LocationDto getLocationInfo() {
    String fileName = getFilename();
    return extractLocationInfoFromFilename(fileName);
  }

  private LocationDto extractLocationInfoFromFilename(String fileName) {
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
      // 예외 발생 시 기본값 사용
    }

    return new LocationDto(departure, destination);
  }

  private boolean isValidLocationName(String location) {
    return location != null && !location.trim().isEmpty();
  }

  public boolean getDeviceStatus() {
    return deviceEntity.isOn();
  }

  public String getCarMdn() {
    return carIdentity.getMdn();
  }

  public String getFilename() {
    return gpsPathService.getRandomGpxFile().getFilename();
  }
}
