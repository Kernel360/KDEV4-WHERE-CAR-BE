package com.where_car.emulator.device.domain.device;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *   에뮬레이터 도메인
 * deviceStatus: 시동 on/off 상태
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-28
 */

@Slf4j
@Getter
@Component
public class DeviceEntity {

  private boolean deviceStatus;

  /**
   * 디바이스 상태 확인
   * @return 디바이스 상태(true: ON, false: OFF)
   */
  public boolean isOn() {
    return this.deviceStatus;
  }

  /**
   * 디바이스 상태를 ON으로 변경
   */
  public void turnOn() {
    this.deviceStatus = true;
    log.debug("디바이스 상태가 ON으로 변경되었습니다.");
  }

  /**
   * 디바이스 상태를 OFF로 변경
   */
  public void turnOff() {
    this.deviceStatus = false;
    log.debug("디바이스 상태가 OFF로 변경되었습니다.");
  }
}
