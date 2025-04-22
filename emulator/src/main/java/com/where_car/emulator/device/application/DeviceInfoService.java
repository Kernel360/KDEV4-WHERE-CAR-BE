package com.where_car.emulator.device.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.where_car.emulator.device.application.dto.CarDto;
import com.where_car.emulator.device.application.dto.CycleInfoDto;
import com.where_car.emulator.device.domain.car.CarIdentity;
import com.where_car.emulator.device.domain.cycle.CarCycleInfo;
import com.where_car.emulator.device.infrastructure.JsonDatabase;
import com.where_car.emulator.gps.application.GpsPathService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *   디바이스 정보 서비스 클래스
 *   차량 데이터 생성과 관련된 기능을 제공합니다.
 * </pre>
 *
 * @since 2025-03-30
 * @version 1.0
 */
@Slf4j
@Service
public class DeviceInfoService {

  private final DeviceEventFactory deviceEventFactory;
  private final DeviceEventExecutor deviceEventExecutor;
  private final GpsPathService gpsPathService;
  private final JsonDatabase jsonDatabase;
  private final CarIdentity carIdentity;
  
  private final List<CarCycleInfo> carCycleInfoList = new ArrayList<>();
  
  @Getter
  private String startTime = "";
  
  @Getter
  private Integer totalDistance = 0;
  
  @Getter
  private int gpsListCount = 0;

  public DeviceInfoService(
      DeviceEventFactory deviceEventFactory,
      DeviceEventExecutor deviceEventExecutor,
      GpsPathService gpsPathService,
      JsonDatabase jsonDatabase,
      CarIdentity carIdentity) {
    this.deviceEventFactory = deviceEventFactory;
    this.deviceEventExecutor = deviceEventExecutor;
    this.gpsPathService = gpsPathService;
    this.jsonDatabase = jsonDatabase;
    this.carIdentity = carIdentity;
    initializeTotalDistance();
  }

  /**
   * 차량 누적 주행거리 초기화
   */
  public void initializeTotalDistance() {
    Optional<CarIdentity> carIdentityOptional = jsonDatabase.getCarIdentityByMdn(carIdentity.getMdn());
    if (carIdentityOptional.isPresent()) {
      CarIdentity loadedCarIdentity = carIdentityOptional.get();
      totalDistance = Integer.valueOf(loadedCarIdentity.getTotalDistance());
      log.info("DB 누적 주행거리 로드: {}", totalDistance);
    } else {
      log.warn("CarIdentity를 찾을 수 없습니다. MDN: {}", carIdentity.getMdn());
    }
  }

  /**
   * 차량 시동 ON 이벤트 생성 및 전송
   */
  public void generateAndSendCarStart() {
    Resource gpxFile = gpsPathService.getRandomGpxFile();
    
    CarDto carStartDto = deviceEventFactory.generateCarStart(
        carIdentity, 
        gpxFile, 
        totalDistance
    );
    
    if (startTime.isEmpty()) {
      startTime = deviceEventFactory.getStartTime();
    }
    
    deviceEventExecutor.sendCarStart(carStartDto);
  }

  /**
   * 차량 주기 정보 생성
   * @return 생성된 주기 정보
   */
  public CarCycleInfo generateCarCycleInfo() {
    Resource gpxFile = gpsPathService.getRandomGpxFile();
    
    CarCycleInfo carCycleInfo = deviceEventFactory.generateCarCycleInfo(
        carCycleInfoList,
        gpxFile,
        gpsListCount,
        totalDistance
    );
    
    carCycleInfoList.add(carCycleInfo);
    gpsListCount++;
    totalDistance = deviceEventFactory.getTotalDistance();

    log.info("CarCycleInfo 생성: {}", carCycleInfo);
    
    return carCycleInfo;
  }

  /**
   * 누적된 주기 정보를 서버에 전송하고 초기화
   */
  public void generateAndSendCycleInfo() {
    if (carCycleInfoList.isEmpty()) {
      return;
    }
    
    CycleInfoDto cycleInfoDto = deviceEventFactory.generateCycleInfo(
        carIdentity,
        carCycleInfoList
    );

    log.info("CycleInfo 생성 ({}): {}", carCycleInfoList.size(), cycleInfoDto);
    deviceEventExecutor.sendCycleInfo(cycleInfoDto);
    carCycleInfoList.clear();
  }

  /**
   * 차량 시동 OFF 이벤트 생성 및 전송
   */
  public void generateAndSendCarStop() {
    Resource gpxFile = gpsPathService.getRandomGpxFile();
    
    CarDto carStopDto = deviceEventFactory.generateCarStop(
        carIdentity,
        gpxFile,
        startTime,
        totalDistance
    );
    
    if (!startTime.isEmpty()) {
      startTime = "";
    }
    
    deviceEventExecutor.sendCarStop(carStopDto);
  }
  
  /**
   * 주기 정보 크기 확인
   * @return 주기 정보 리스트 크기
   */
  public int getCycleInfoListSize() {
    return carCycleInfoList.size();
  }
}
