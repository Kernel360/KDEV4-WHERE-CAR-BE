package com.where_car.emulator.device.domain.event;

import com.where_car.emulator.device.domain.car.CarDevice;
import com.where_car.emulator.device.domain.car.CarIdentity;
import com.where_car.emulator.device.domain.cycle.CarCycleInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * <pre>
 *   시동 ON 정보 전달 도메인
 *   carIdentity: 클래스 주석 참조
 *   carDevice: 클래스 주석 참조
 *   onTime: 차량 on 시간 | ccyyMMddHHmm
 *   offTime: 차량 off 시간 | ccyyMMddHHmm
 *   carCycleInfo: 클래스 주석 참조
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-27
 */

@Getter
@ToString
public class CarStart {

  private final CarIdentity carIdentity;
  private final CarDevice carDevice;

  private final String onTime;
  private final String offTime;

  private final CarCycleInfo cycleInfo;

  @Builder
  public CarStart(CarIdentity carIdentity, CarDevice carDevice, String onTime, String offTime, CarCycleInfo cycleInfo) {

    if (carIdentity.getMdn() == null) {
        throw new IllegalArgumentException("MDN 값이 없습니다.");
    }

    this.carIdentity = carIdentity;
    this.carDevice = carDevice;
    this.onTime = onTime;
    this.offTime = offTime;
    this.cycleInfo = cycleInfo;
  }
}
