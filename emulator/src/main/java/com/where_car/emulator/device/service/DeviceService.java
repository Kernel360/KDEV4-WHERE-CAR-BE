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
import com.where_car.emulator.global.constants.DateConstant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
  private ScheduledExecutorService scheduler; // 스케줄러를 사용하기 위해 라이브러리 호출

  private final CarIdentity carIdentity;
  private final DeviceEntity deviceEntity;

  private final List<CarCycleInfo> carCycleInfoList = new ArrayList<>();

  private static final int RETRY_DELAY_SECONDS = 60;
  private static LocalDateTime START_TIME = null;

  public DeviceService(RestTemplate restTemplate, CarIdentity carIdentity, DeviceEntity deviceEntity) {
    this.restTemplate = restTemplate;
    this.carIdentity = carIdentity;
    this.deviceEntity = deviceEntity;
  }

  /**
   *  에뮬레이터 상태 변경
   *  Key-On: 시동 ON 정보 전송 -> 1초 마다 차량 주기 정보 생성 -> 1분 마다 전체 차량 주기 정보 생성 ->
   *  Key-Off: 정보 생성
   */
  public void toggleDevice() {
    if (!isDeviceStatus()) {
      log.info("차량의 Key-On 신호가 감지되었습니다, 스케줄러를 작동합니다.");
      setDeviceStatus(true);
      generateCarStart();
      startScheduler();
    } else {
      log.info("차량의 Key-Off 신호가 감지되었습니다, 스케줄러를 중지합니다.");
      setDeviceStatus(false);
      stopScheduler();
      generateCarStop();
    }
  }

  private void startScheduler() {
    scheduler = Executors.newScheduledThreadPool(2);
    scheduler.scheduleWithFixedDelay(this::generateCarCycleInfo, 0, 1000, TimeUnit.MILLISECONDS);
    scheduler.scheduleWithFixedDelay(this::generateCycleInfo, 0, 60000, TimeUnit.MILLISECONDS);
  }

  private void stopScheduler() {
    if (scheduler != null && !scheduler.isShutdown()) {
      sendCycleInfo();
      scheduler.shutdown();
    }
  }

  public void generateCarStart() {
    CarStart carStart = createCarStart();
    CarStartDto carStartDto = createCarStartDto(carStart);

    if (START_TIME == null) {
      START_TIME = carStartDto.getOnTime(); // 시동 시간 저장했다가 key-off 시에 사용
    }

    log.info("CarStartData 생성: {}", carStartDto);
  }

  public void generateCarCycleInfo() {

    String previousSecond = carCycleInfoList.isEmpty() ? "00" : String.format("%02d", (Integer.parseInt(carCycleInfoList.get(carCycleInfoList.size() - 1).getSec()) + 1) % 60);
    int batteryValue = generateRandomBatteryValue();

    CarCycleInfo carCycleInfo = CarCycleInfo.builder()
        .sec(previousSecond)
        .gcd("0") // GPS 미구현으로 일단 0으로 처리함
        .lat(0)
        .lon(0)
        .ang(0)
        .spd(0)
        .sum(0)
        .bat(batteryValue) // 80% 확률로 15v ~ 12v 사이의 값, 15% 확률로 12v ~ 10v 사이의 값, 5% 확률로 10v ~ 8v 사이의 값
        .build();

    carCycleInfoList.add(carCycleInfo);

    log.info("CarCycleInfo 생성: {}", carCycleInfo);
  }

  public void generateCycleInfo() {

    CycleInfo cycleInfo = createCycleInfo();
    CycleInfoDto cycleInfoDto = createCycleInfoDto(cycleInfo);

    // carCycleInfoList에 60개가 쌓이면 전송 후 리스트 초기화
    if (cycleInfoDto.getCCnt() == 60) {
      log.info("CycleInfo 자동 생성 ({}): {}", cycleInfoDto.getCCnt(), cycleInfoDto);
      // TODO: CycleInfo 전송 로직 추가
      carCycleInfoList.clear();
    }
  }

  private void sendCycleInfo() {

    CycleInfo cycleInfo = createCycleInfo();
    CycleInfoDto cycleInfoDto = createCycleInfoDto(cycleInfo);

    log.info("CycleInfo 중단 생성 ({}): {}", cycleInfoDto.getCCnt(), cycleInfoDto);
    // TODO: CycleInfo 전송 로직 추가
    carCycleInfoList.clear();
  }

  public void generateCarStop() {
    CarStop carStop = createCarStop();
    CarStopDto carStopDto = createCarStopDto(carStop);

    if (START_TIME != null) {
      START_TIME = null; // 시동 시간 초기화
    }

    log.info("CarStopData 생성: {}", carStopDto);
  }

  private CarStart createCarStart() {
    return CarStart.builder()
        .carIdentity(carIdentity)
        .carDevice(CarDevice.builder().build())
        .onTime(LocalDateTime.parse(LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER), DateConstant.DATE_TIME_FORMATTER))
        .offTime(null)
        .cycleInfo(CarCycleInfo.builder().gcd("0").lat(0).lon(0).ang(0).spd(0).sum(0).build())
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
        .oTime(LocalDateTime.parse(LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER), DateConstant.DATE_TIME_FORMATTER))
        .cCnt(carCycleInfoList.size())
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
    return CarStop.builder()
        .carIdentity(carIdentity)
        .carDevice(CarDevice.builder().build())
        .onTime(START_TIME)
        .offTime(LocalDateTime.parse(LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER), DateConstant.DATE_TIME_FORMATTER))
        .cycleInfo(CarCycleInfo.builder().gcd("0").lat(0).lon(0).ang(0).spd(0).sum(0).build())
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

  public boolean isDeviceStatus() {
    return deviceEntity.isDeviceStatus();
  }

  public void setDeviceStatus(boolean status) {
    deviceEntity.setDeviceStatus(status);
  }

  public CarIdentity fetchCarIdentity() {
    return carIdentity;
  }
}
