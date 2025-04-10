package com.where_car.emulator.device.service;

import com.where_car.emulator.device.domain.CarStart;
import com.where_car.emulator.device.domain.CarStop;
import com.where_car.emulator.device.domain.CycleInfo;
import com.where_car.emulator.device.domain.DeviceEntity;
import com.where_car.emulator.device.domain.common.CarCycleInfo;
import com.where_car.emulator.device.domain.common.CarDevice;
import com.where_car.emulator.device.domain.common.CarIdentity;
import com.where_car.emulator.device.dto.CarStartDto;
import com.where_car.emulator.device.dto.CarStopDto;
import com.where_car.emulator.device.dto.CycleInfoDto;
import com.where_car.emulator.device.repository.JsonDatabase;
import com.where_car.emulator.global.constants.DateConstant;
import com.where_car.emulator.global.error.DeviceErrorCode;
import com.where_car.emulator.global.error.DeviceException;
import com.where_car.emulator.gps_module.service.GpsPathService;
import com.where_car.emulator.gps_module.service.GpsService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;

/**
 * <pre>
 *   디바이스 서비스 클래스
 *   에뮬레이터의 상태를 관리하고 주기적으로 데이터를 생성 및 전송합니다.
 * </pre>
 *
 * @since 2025-03-30
 * @author Changil.kim
 * @version 1.0
 */
@Slf4j
@Service
public class DeviceService {

  private final RestTemplate restTemplate;
  private final GpsService gpsService;
  private ScheduledExecutorService scheduler; // 스케줄러를 사용하기 위해 라이브러리 호출

  private final CarIdentity carIdentity;
  private final DeviceEntity deviceEntity;

  private final GpsPathService gpsPathService;
  private final JsonDatabase jsonDatabase;

  private final List<CarCycleInfo> carCycleInfoList = new ArrayList<>();

  private static final int RETRY_DELAY_SECONDS = 60;
  private static String START_TIME = ""; // 시작 시간 저장
  private static Integer TOTAL_DISTANCE = 0; // 총 주행 거리 저장
  private static int GPS_LIST_COUNT = 0; // GPS 리스트 카운트

  public DeviceService(RestTemplate restTemplate, DeviceEntity deviceEntity,
      GpsPathService gpsPathService, GpsService gpsService, CarIdentity carIdentity,
      JsonDatabase jsonDatabase) {
    this.restTemplate = restTemplate;
    this.deviceEntity = deviceEntity;
    this.gpsPathService = gpsPathService;
    this.gpsService = gpsService;
    this.carIdentity = carIdentity;
    this.jsonDatabase = jsonDatabase;
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
    //scheduler.scheduleAtFixedRate(this::generateCycleInfo, 60, 60, TimeUnit.SECONDS);
  }

  private void stopScheduler() {
    if (scheduler != null && !scheduler.isShutdown()) {
      scheduler.shutdown();
      generateCycleInfo();
    }
  }

  public void generateCarStart() {
    CarStart carStart = createCarStart();
    CarStartDto carStartDto = createCarStartDto(carStart);

    if (Objects.equals(START_TIME, "")) {
      START_TIME = carStartDto.getOnTime(); // 시동 시간 저장했다가 key-off 시에 사용
    }

    log.info("CarStartData 생성: {}", carStartDto);
    sendRequestWithRetry("/api/on", carStartDto, "시동 ON 정보 API");
  }

  public void generateCarCycleInfo() {

    String previousSecond = carCycleInfoList.isEmpty() ? "00" : String.format("%02d", (Integer.parseInt(carCycleInfoList.get(carCycleInfoList.size() - 1).getSec()) + 1) % 60);
    int batteryValue = generateRandomBatteryValue();

    Resource gpxFile = gpsPathService.getRandomGpxFile();
    List<Element> allTrkpts = gpsPathService.getAllTrkpts(gpxFile);

    String curLat = allTrkpts.get(GPS_LIST_COUNT).getAttribute("lat");
    String curLon = allTrkpts.get(GPS_LIST_COUNT).getAttribute("lon");
    String preLat = allTrkpts.get(GPS_LIST_COUNT != 0 ? GPS_LIST_COUNT - 1 : GPS_LIST_COUNT).getAttribute("lat");
    String preLon = allTrkpts.get(GPS_LIST_COUNT != 0 ? GPS_LIST_COUNT - 1 : GPS_LIST_COUNT).getAttribute("lon");

    int ang = gpsService.calculateBearing(
        Double.parseDouble(preLat),
        Double.parseDouble(preLon),
        Double.parseDouble(curLat),
        Double.parseDouble(curLon)
    );

    int spd = (int) Math.round(gpsService.calculateSpeed(
        gpsService.calculateDistance(
            Double.parseDouble(preLat),
            Double.parseDouble(preLon),
            Double.parseDouble(curLat),
            Double.parseDouble(curLon)
        ),
        1
    ));

    double distance = gpsService.calculateDistance(Double.parseDouble(preLat),
        Double.parseDouble(preLon),
        Double.parseDouble(curLat),
        Double.parseDouble(curLon));

    TOTAL_DISTANCE += (int) Math.round(distance);

    CarCycleInfo carCycleInfo = CarCycleInfo.builder()
        .sec(previousSecond)
        .gcd("A") // GPS 미구현으로 일단 0으로 처리함
        .lat(formatCoordinate(curLat))
        .lon(formatCoordinate(curLon))
        .ang(String.valueOf(ang))
        .spd(String.valueOf(spd))
        .sum(String.valueOf(TOTAL_DISTANCE))
        .bat(String.valueOf(batteryValue)) // 80% 확률로 15v ~ 12v 사이의 값, 15% 확률로 12v ~ 10v 사이의 값, 5% 확률로 10v ~ 8v 사이의 값
        .build();

    carCycleInfoList.add(carCycleInfo);
    GPS_LIST_COUNT++;

    log.info("CarCycleInfo 생성: {}", carCycleInfo);

    if (carCycleInfoList.size() == 60) {
      generateCycleInfo();
    }
  }

  public void generateCycleInfo() {

    CycleInfo cycleInfo = createCycleInfo();
    CycleInfoDto cycleInfoDto = createCycleInfoDto(cycleInfo);

    log.info("CycleInfo 생성 ({}): {}", carCycleInfoList.size(), cycleInfoDto);
    sendRequestWithRetry("/api/gps", cycleInfoDto, "주기 정보 API");
    carCycleInfoList.clear();
  }

  public void generateCarStop() {
    CarStop carStop = createCarStop();
    CarStopDto carStopDto = createCarStopDto(carStop);

    if (START_TIME != "") {
      START_TIME = ""; // 시동 시간 초기화
    }

    log.info("CarStopData 생성: {}", carStopDto);
    sendRequestWithRetry("/api/off", carStopDto, "시동 OFF 정보 API");
  }

  private CarStart createCarStart() {

    Resource gpxFile = gpsPathService.getRandomGpxFile();
    List<Element> firstTrkpt = gpsPathService.getFirstTrkpt(gpxFile);

    String angle = calculateAngleFromCoordinates(firstTrkpt);
    String speed = calculateSpeedFromCoordinates(firstTrkpt);

    return CarStart.builder()
        .carIdentity(carIdentity)
        .carDevice(CarDevice.builder().build())
        .onTime(LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER))
        .offTime("")
        .cycleInfo(CarCycleInfo.builder()
            .gcd("A")
            .lat(formatCoordinate(firstTrkpt.get(0).getAttribute("lat")))
            .lon(formatCoordinate(firstTrkpt.get(0).getAttribute("lon")))
            .ang(angle)
            .spd(speed)
            .sum(String.valueOf(TOTAL_DISTANCE))
            .build())
        .build();
  }

  private CarStartDto createCarStartDto(CarStart carStart) {
    return CarStartDto.builder()
        .mdn(carStart.getCarIdentity().getMdn())
        .tid(carStart.getCarDevice().getTid())
        .mid(carStart.getCarDevice().getMid())
        .pv(carStart.getCarDevice().getPv())
        .did(carStart.getCarDevice().getDid())
        .onTime(carStart.getOnTime())
        .offTime(carStart.getOffTime())
        .gcd(carStart.getCycleInfo().getGcd())
        .lat(carStart.getCycleInfo().getLat())
        .lon(carStart.getCycleInfo().getLon())
        .ang(carStart.getCycleInfo().getAng())
        .spd(carStart.getCycleInfo().getSpd())
        .sum(carStart.getCycleInfo().getSum())
        .build();
  }

  private CycleInfo createCycleInfo() {
    return CycleInfo.builder()
        .carIdentity(carIdentity)
        .carDevice(CarDevice.builder().build())
        .oTime(LocalDateTime.now().format(DateConstant.DATE_TIME_MINUTE_FORMATTE))
        .cCnt(String.valueOf(carCycleInfoList.size()))
        .cList(carCycleInfoList)
        .build();
  }

  private CycleInfoDto createCycleInfoDto(CycleInfo cycleInfo) {
    return CycleInfoDto.builder()
        .mdn(cycleInfo.getCarIdentity().getMdn())
        .tid(cycleInfo.getCarDevice().getTid())
        .mid(cycleInfo.getCarDevice().getMid())
        .pv(cycleInfo.getCarDevice().getPv())
        .did(cycleInfo.getCarDevice().getDid())
        .oTime(cycleInfo.getOTime())
        .cCnt(cycleInfo.getCCnt())
        .cList(cycleInfo.getCList())
        .build();
  }

  private CarStop createCarStop() {

    Resource gpxFile = gpsPathService.getRandomGpxFile();
    List<Element> lastTrkpt = gpsPathService.getLastTrkpt(gpxFile);

    String angle = calculateAngleFromCoordinates(lastTrkpt);
    String speed = calculateSpeedFromCoordinates(lastTrkpt);

    CarIdentity updatedCarIdentity = new CarIdentity();
    updatedCarIdentity.setMdn(carIdentity.getMdn());
    updatedCarIdentity.setVrp(carIdentity.getVrp());
    updatedCarIdentity.setTotalDistance(String.valueOf(TOTAL_DISTANCE));
    jsonDatabase.updateCarIdentity(updatedCarIdentity);

    return CarStop.builder()
        .carIdentity(carIdentity)
        .carDevice(CarDevice.builder().build())
        .onTime(START_TIME)
        .offTime(LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER))
        .cycleInfo(CarCycleInfo.builder()
            .gcd("A")
            .lat(formatCoordinate(lastTrkpt.get(0).getAttribute("lat")))
            .lon(formatCoordinate(lastTrkpt.get(0).getAttribute("lon")))
            .ang(angle)
            .spd(speed)
            .sum(String.valueOf(TOTAL_DISTANCE))
            .build())
        .build();
  }

  private CarStopDto createCarStopDto(CarStop carStop) {
    return CarStopDto.builder()
        .mdn(carStop.getCarIdentity().getMdn())
        .tid(carStop.getCarDevice().getTid())
        .mid(carStop.getCarDevice().getMid())
        .pv(carStop.getCarDevice().getPv())
        .did(carStop.getCarDevice().getDid())
        .onTime(carStop.getOnTime())
        .offTime(carStop.getOffTime())
        .gcd(carStop.getCycleInfo().getGcd())
        .lat(carStop.getCycleInfo().getLat())
        .lon(carStop.getCycleInfo().getLon())
        .ang(carStop.getCycleInfo().getAng())
        .spd(carStop.getCycleInfo().getSpd())
        .sum(carStop.getCycleInfo().getSum())
        .build();
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
      Thread.sleep(RETRY_DELAY_SECONDS * 1000);
      sendRequestWithRetry(url, requestDto, action);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("{} 정보 재시도 중 인터럽트 발생: {}", action, e.getMessage());
    }
  }

  private String formatCoordinate(String value) {
    // 문자열을 double로 변환
    double doubleValue = Double.parseDouble(value);
    // 소수점 6자리까지 포맷
    String formattedValue = String.format("%.6f", doubleValue);
    // 소수점 제거
    return formattedValue.replace(".", "");
  }

  private String calculateSpeedFromCoordinates(List<Element> firstTrkpt) {
    int speed = (int) Math.round(gpsService.calculateSpeed(
        gpsService.calculateDistance(
            Double.parseDouble(firstTrkpt.get(0).getAttribute("lat")),
            Double.parseDouble(firstTrkpt.get(0).getAttribute("lon")),
            Double.parseDouble(firstTrkpt.get(1).getAttribute("lat")),
            Double.parseDouble(firstTrkpt.get(1).getAttribute("lon"))
        ),
        1
    ));
    return String.valueOf(speed);
  }

  private String calculateAngleFromCoordinates(List<Element> firstTrkpt) {
    int angle = gpsService.calculateBearing(
        Double.parseDouble(firstTrkpt.get(0).getAttribute("lat")),
        Double.parseDouble(firstTrkpt.get(0).getAttribute("lon")),
        Double.parseDouble(firstTrkpt.get(1).getAttribute("lat")),
        Double.parseDouble(firstTrkpt.get(1).getAttribute("lon"))
    );
    return String.valueOf(angle);
  }

  private int generateRandomBatteryValue() {
    Random random = new Random();
    double probability = random.nextDouble();

    if (probability < 0.8) {
      // 80% 확률로 15v ~ 12v 사이의 값
      return (int) (12 + (15 - 12) * random.nextDouble());
    } else if (probability < 0.95) {
      // 15% 확률로 12v ~ 10v 사이의 값
      return (int) (10 + (12 - 10) * random.nextDouble());
    } else {
      // 5% 확률로 10v ~ 8v 사이의 값
      return (int) (8 + (10 - 8) * random.nextDouble());
    }
  }

  public void initializeTotalDistance() {
    Optional<CarIdentity> carIdentityOptional = jsonDatabase.getCarIdentityByMdn(carIdentity.getMdn());
    if (carIdentityOptional.isPresent()) {
      CarIdentity carIdentity = carIdentityOptional.get();
      TOTAL_DISTANCE = Integer.valueOf(carIdentity.getTotalDistance());
      log.info("DB 누적 주행거리 로드: {}", TOTAL_DISTANCE);
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
