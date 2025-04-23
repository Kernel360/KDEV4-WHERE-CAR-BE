package com.where_car.emulator.device.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.where_car.emulator.device.application.dto.CarRequest;
import com.where_car.emulator.device.application.dto.CycleInfoRequest;
import com.where_car.emulator.device.domain.car.CarIdentity;
import com.where_car.emulator.device.domain.cycle.CarCycleInfo;
import com.where_car.emulator.device.infrastructure.JsonDatabase;
import com.where_car.emulator.gps.application.GpsPathService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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

  public void generateAndSendCarStart() {
    Resource gpxFile = gpsPathService.getRandomGpxFile();
    
    CarRequest carStartDto = deviceEventFactory.generateCarStart(
        carIdentity, 
        gpxFile, 
        totalDistance
    );
    
    if (startTime.isEmpty()) {
      startTime = deviceEventFactory.getStartTime();
    }
    
    deviceEventExecutor.sendCarStart(carStartDto);
  }

  public void generateCarCycleInfo() {
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
  }

  /**
   * 누적된 주기 정보를 서버에 전송하고 초기화
   */
  public void generateAndSendCycleInfo() {
    if (carCycleInfoList.isEmpty()) {
      return;
    }
    
    CycleInfoRequest cycleInfoRequest = deviceEventFactory.generateCycleInfo(
        carIdentity,
        carCycleInfoList
    );

    log.info("CycleInfo 생성 ({}): {}", carCycleInfoList.size(), cycleInfoRequest);
    deviceEventExecutor.sendCycleInfo(cycleInfoRequest);
    carCycleInfoList.clear();
  }

  /**
   * 차량 시동 OFF 이벤트 생성 및 전송
   */
  public void generateAndSendCarStop() {
    Resource gpxFile = gpsPathService.getRandomGpxFile();
    
    CarRequest carStopDto = deviceEventFactory.generateCarStop(
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

