package com.where_car.emulator.device.service;

import com.where_car.emulator.device.domain.CarStart;
import com.where_car.emulator.device.domain.DeviceEntity;
import com.where_car.emulator.device.domain.common.CarCycleInfo;
import com.where_car.emulator.device.domain.common.CarDevice;
import com.where_car.emulator.device.domain.common.CarIdentity;
import com.where_car.emulator.device.dto.CarStartDto;
import com.where_car.emulator.global.constants.DateConstant;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceService {

  CarIdentity carIdentity;
  DeviceEntity deviceEntity;

  public DeviceService(CarIdentity carIdentity, DeviceEntity deviceEntity) {
    this.carIdentity = carIdentity;
    this.deviceEntity = deviceEntity;
  }

  public void deviceActivateScheduler() {
    if (!deviceEntity.isDeviceStatus()) {
      startScheduler();
    } else {
      stopScheduler();
    }
  }

  private void startScheduler() {
    log.info("디바이스 스케줄러 운행 시작");
    deviceEntity.setDeviceStatus(true);
    sendCarStart();
  }

  public CarStartDto sendCarStart() {
    if (!deviceEntity.isDeviceStatus()) {
      log.error("디바이스가 비활성화 상태입니다.");
    }

    CarStart carStart = CarStart.builder()
        .carIdentity(carIdentity)
        .carDevice(CarDevice.builder().build())
        .onTime(
            LocalDateTime.parse(
                LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER),
                DateConstant.DATE_TIME_FORMATTER))
        .offTime(null)
        .cycleInfo(CarCycleInfo.builder()
            .gcd("0")
            .lat(0)
            .lon(0)
            .ang(0)
            .spd(0)
            .sum(0)
            .build())
        .build();

    log.info("디바이스 시동 ON 정보 전송 = {}", carStart.toString());

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

  private void stopScheduler() {
    // TODO: 스케줄러 비활성화 로직 구현
    log.info("디바이스 스케줄러 운행 종료");
    deviceEntity.setDeviceStatus(false);
    sendCarStop();
  }

  private void sendCarStop() {

  }

  public boolean isDeviceStatus() {
    return deviceEntity.isDeviceStatus();
  }

  public CarIdentity fetchCarIdentity() {
    return carIdentity;
  }
}
