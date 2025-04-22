package com.where_car.emulator.device.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;

import com.where_car.emulator.device.application.dto.CarDto;
import com.where_car.emulator.device.application.dto.CycleInfoDto;
import com.where_car.emulator.device.domain.DeviceFactory;
import com.where_car.emulator.device.domain.car.CarDevice;
import com.where_car.emulator.device.domain.car.CarIdentity;
import com.where_car.emulator.device.domain.cycle.CarCycleInfo;
import com.where_car.emulator.device.domain.cycle.CycleInfo;
import com.where_car.emulator.device.domain.device.DeviceEntity;
import com.where_car.emulator.device.domain.event.CarStart;
import com.where_car.emulator.device.domain.event.CarStop;
import com.where_car.emulator.device.infrastructure.JsonDatabase;
import com.where_car.emulator.global.constants.DateConstant;
import com.where_car.emulator.global.error.DeviceErrorCode;
import com.where_car.emulator.global.error.DeviceException;
import com.where_car.emulator.global.utill.GpsUtils;
import com.where_car.emulator.global.utill.RandomUtils;
import com.where_car.emulator.global.utill.StringUtils;
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

  private final RestTemplate restTemplate;
  private ScheduledExecutorService scheduler; // 빈으로 주입되지 않으므로 final 제거

  private final CarIdentity carIdentity;
  private final DeviceEntity deviceEntity;

  private final GpsPathService gpsPathService;
  private final JsonDatabase jsonDatabase;

  private final DeviceFactory deviceFactory;

  private final List<CarCycleInfo> carCycleInfoList = new ArrayList<>();

  private static final int RETRY_DELAY_SECONDS = 60;
  private String startTime = ""; // 빈으로 주입되지 않으므로 생성자에서 제외
  private Integer totalDistance = 0; // 빈으로 주입되지 않으므로 생성자에서 제외
  private int gpsListCount = 0; // 빈으로 주입되지 않으므로 생성자에서 제외

  // 생성자를 수동으로 정의해서 필요한 빈만 주입받도록 합니다
  public DeviceService(RestTemplate restTemplate,
                      CarIdentity carIdentity,
                      DeviceEntity deviceEntity,
                      GpsPathService gpsPathService,
                      JsonDatabase jsonDatabase,
                      DeviceFactory deviceFactory) {
    this.restTemplate = restTemplate;
    this.carIdentity = carIdentity;
    this.deviceEntity = deviceEntity;
    this.gpsPathService = gpsPathService;
    this.jsonDatabase = jsonDatabase;
    this.deviceFactory = deviceFactory;
    // 다른 필드(scheduler, startTime, totalDistance, gpsListCount)는 이미 위에서 초기화됨
  }

  @PostConstruct
  public void init() {
    initializeTotalDistance();
    log.info("에뮬레이터의 주행 경로를 선택했습니다: {}", gpsPathService.getRandomGpxFile().getFilename());
  }

  /**
   *  에뮬레이터 상태 변경
   *  Key-On: 시동 ON 정보 전송 -> 1초 마다 차량 주기 정보 생성 -> 1분 마다 전체 차량 주기 정보 생성 ->
   *  Key-Off: 정보 생성
   */
  public void toggleDevice() {
    if (!isDeviceStatus()) {
      try {
        log.info("차량의 Key-On 신호가 감지되었습니다, 스케줄러를 작동합니다.");
        setDeviceStatus(true);
        generateCarStart();
        startScheduler();
      } catch (Exception e) {
        setDeviceStatus(false); // 상태를 원래대로 되돌림
        log.error("차량 시동 과정에서 오류 발생: {}", e.getMessage());
        throw new DeviceException(DeviceErrorCode.DEVICE_START_FAILED, e);
      }
    } else {
      try {
        log.info("차량의 Key-Off 신호가 감지되었습니다, 스케줄러를 중지합니다.");
        setDeviceStatus(false);
        stopScheduler();
        generateCarStop();
      } catch (Exception e) {
        setDeviceStatus(true); // 상태를 원래대로 되돌림
        log.error("차량 시동 종료 과정에서 오류 발생: {}", e.getMessage());
        throw new DeviceException(DeviceErrorCode.DEVICE_STOP_FAILED, e);
      }
    }
  }

  private void startScheduler() {
    scheduler = Executors.newScheduledThreadPool(2);
    scheduler.scheduleAtFixedRate(this::generateCarCycleInfo, 0, 1, TimeUnit.SECONDS);
  }

  private void stopScheduler() {
    if (scheduler != null && !scheduler.isShutdown()) {
      scheduler.shutdown();
      generateCycleInfo();
    }
  }

  public void generateCarStart() {
    Resource gpxFile = gpsPathService.getRandomGpxFile();
    List<Element> firstTrkpt = gpsPathService.getFirstTrkpt(gpxFile);

    // 도메인 입력을 위한 데이터 변환 처리
    String latitude = StringUtils.formatCoordinate(firstTrkpt.get(0).getAttribute("lat"));
    String longitude = StringUtils.formatCoordinate(firstTrkpt.get(0).getAttribute("lon"));
    String angle = StringUtils.calculateAngleFromCoordinates(firstTrkpt);
    String speed = StringUtils.calculateSpeedFromCoordinates(firstTrkpt);

    CarCycleInfo carCycleInfo = CarCycleInfo.builder()
        .sec("00")
        .gcd("A")
        .lat(latitude)
        .lon(longitude)
        .ang(angle)
        .spd(speed)
        .sum(String.valueOf(totalDistance))
        .bat("0")
        .build();

    CarStart carStart = CarStart.builder()
        .carIdentity(carIdentity)
        .carDevice(CarDevice.builder().build())
        .onTime(LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER))
        .offTime("")
        .cycleInfo(carCycleInfo)
        .build();

    if (Objects.equals(startTime, "")) {
      startTime = carStart.getOnTime(); // 시동 시간 저장했다가 key-off 시에 사용
    }

    CarDto carStartDto = deviceFactory.createCarStartDto(carStart);
    log.info("CarStartData 생성: {}", carStartDto);
    sendRequestWithRetry("/api/on", carStartDto, "시동 ON 정보 API");
  }

  public void generateCarCycleInfo() {

    String previousSecond = carCycleInfoList.isEmpty() ? "00" : String.format("%02d", (Integer.parseInt(carCycleInfoList.get(carCycleInfoList.size() - 1).getSec()) + 1) % 60);
    int batteryValue = RandomUtils.generateRandomBatteryValue();

    Resource gpxFile = gpsPathService.getRandomGpxFile();
    List<Element> allTrkpts = gpsPathService.getAllTrkpts(gpxFile);

    // 데이터 변환 및 계산 처리
    String curLat = allTrkpts.get(gpsListCount).getAttribute("lat");
    String curLon = allTrkpts.get(gpsListCount).getAttribute("lon");
    String preLat = allTrkpts.get(gpsListCount != 0 ? gpsListCount - 1 : gpsListCount).getAttribute("lat");
    String preLon = allTrkpts.get(gpsListCount != 0 ? gpsListCount - 1 : gpsListCount).getAttribute("lon");

    double distance = GpsUtils.calculateDistance(Double.parseDouble(preLat),
        Double.parseDouble(preLon),
        Double.parseDouble(curLat),
        Double.parseDouble(curLon));

    totalDistance += (int) Math.round(distance);

    int angle = GpsUtils.calculateBearing(
        Double.parseDouble(preLat),
        Double.parseDouble(preLon),
        Double.parseDouble(curLat),
        Double.parseDouble(curLon)
    );

    int speed = (int) Math.round(GpsUtils.calculateSpeed(distance, 1));

    CarCycleInfo carCycleInfo = CarCycleInfo.builder()
        .sec(previousSecond)
        .gcd("A")
        .lat(StringUtils.formatCoordinate(curLat))
        .lon(StringUtils.formatCoordinate(curLon))
        .ang(String.valueOf(angle))
        .spd(String.valueOf(speed))
        .sum(String.valueOf(totalDistance))
        .bat(String.valueOf(batteryValue))
        .build();

    carCycleInfoList.add(carCycleInfo);
    gpsListCount++;

    log.info("CarCycleInfo 생성: {}", carCycleInfo);

    if (carCycleInfoList.size() == 60) {
      generateCycleInfo();
    }
  }

  public void generateCycleInfo() {

    CycleInfo cycleInfo = CycleInfo.builder()
        .carIdentity(carIdentity)
        .carDevice(CarDevice.builder().build())
        .oTime(LocalDateTime.now().format(DateConstant.DATE_TIME_MINUTE_FORMATTER))
        .cCnt(String.valueOf(carCycleInfoList.size()))
        .cList(carCycleInfoList)
        .build();

    CycleInfoDto cycleInfoDto = deviceFactory.createCycleInfoDto(cycleInfo);

    log.info("CycleInfo 생성 ({}): {}", carCycleInfoList.size(), cycleInfoDto);
    sendRequestWithRetry("/api/gps", cycleInfoDto, "주기 정보 API");
    carCycleInfoList.clear();
  }

  public void generateCarStop() {
    Resource gpxFile = gpsPathService.getRandomGpxFile();
    List<Element> lastTrkpt = gpsPathService.getLastTrkpt(gpxFile);

    // 도메인 입력을 위한 데이터 변환 처리
    String latitude = StringUtils.formatCoordinate(lastTrkpt.get(0).getAttribute("lat"));
    String longitude = StringUtils.formatCoordinate(lastTrkpt.get(0).getAttribute("lon"));
    String angle = StringUtils.calculateAngleFromCoordinates(lastTrkpt);
    String speed = StringUtils.calculateSpeedFromCoordinates(lastTrkpt);

    CarCycleInfo carCycleInfo = CarCycleInfo.builder()
        .sec("00")
        .gcd("A")
        .lat(latitude)
        .lon(longitude)
        .ang(angle)
        .spd(speed)
        .sum(String.valueOf(totalDistance))
        .bat("0")
        .build();

    CarStop carStop = CarStop.builder()
        .carIdentity(carIdentity)
        .carDevice(CarDevice.builder().build())
        .onTime(startTime)
        .offTime(LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER))
        .cycleInfo(carCycleInfo)
        .build();

    CarDto carStopDto = deviceFactory.createCarStopDto(carStop);

    if (!Objects.equals(startTime, "")) {
      startTime = ""; // 시동 시간 초기화
    }

    log.info("CarStopData 생성: {}", carStopDto);
    sendRequestWithRetry("/api/off", carStopDto, "시동 OFF 정보 API");
  }

  private void sendRequestWithRetry(String url, Object requestDto, String action) {
    try {
      restTemplate.postForObject(url, requestDto, requestDto.getClass());
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        log.error("{} 정보 전달 실패: 서버 API를 호출할 수 없습니다, 1분 후 재시도 합니다.", action);
      }
      else {
        log.error("{} 정보 전달 실패: 알 수 없는 오류가 발생했습니다, 1분 후 재시도 합니다. {}", action, e.getMessage());
      }
      retryAfterDelay(url, requestDto, action);
    }
  }

  private void retryAfterDelay(String url, Object requestDto, String action) {
    try {
      Thread.sleep(RETRY_DELAY_SECONDS * 1000L);
      sendRequestWithRetry(url, requestDto, action);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("{} 정보 재시도 중 인터럽트 발생: {}", action, e.getMessage());
    }
  }

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

  public boolean isDeviceStatus() {
    return deviceEntity.isDeviceStatus();
  }

  public void setDeviceStatus(boolean status) {
    deviceEntity.setDeviceStatus(status);
  }

  public CarIdentity fetchCarIdentity() {
    return carIdentity;
  }

  public String getFilename() {
    return gpsPathService.getRandomGpxFile().getFilename();
  }
}
