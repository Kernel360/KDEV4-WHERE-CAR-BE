package com.where_car.emulator.device.domain.event;

import com.where_car.emulator.device.domain.car.CarDevice;
import com.where_car.emulator.device.domain.car.CarIdentity;
import com.where_car.emulator.global.constants.DeviceConstant;

import lombok.Builder;
import lombok.Getter;

/**
 * <pre>
 *   제어정보 요청 | 후순위
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-27
 */

@Getter
@Builder
public class GetSetInfo {

  private CarIdentity carIdentity;
  private CarDevice carDevice;

  private String onTime;

  @Builder.Default
  private String dFWVer = DeviceConstant.FIRMWARE_VERSION;
}
