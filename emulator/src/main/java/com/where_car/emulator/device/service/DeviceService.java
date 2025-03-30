package com.where_car.emulator.device.service;

import com.where_car.emulator.device.domain.DeviceEntity;
import com.where_car.emulator.device.domain.common.CarIdentity;
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

  public void activateScheduler() {
    if (!deviceEntity.isDeviceStatus()) {
      // TODO: 스케줄러 활성화 로직 구현
      deviceEntity.setDeviceStatus(true);
      log.info("디바이스 스케줄러 운행 시작");
    } else {
      deviceEntity.setDeviceStatus(false);
      log.info("디바이스 스케줄러 운행 종료");
    }
  }

  public boolean fetchDeviceStatus() {
    return deviceEntity.isDeviceStatus();
  }

  public CarIdentity fetchCarIdentity() {
    return carIdentity;
  }
}
