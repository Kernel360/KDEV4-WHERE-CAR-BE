package com.where_car.emulator.device.domain;

import com.where_car.emulator.device.domain.common.CarCycleInfo;
import com.where_car.emulator.device.domain.common.CarDevice;
import com.where_car.emulator.device.domain.common.CarIdentity;
import lombok.Builder;
import lombok.Getter;

/**
 * <pre>
 *   시동 OFF 전달 도메인
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
@Builder
public class CarStop {

  private CarIdentity carIdentity;
  private CarDevice carDevice;

  private String onTime;
  private String offTime;

  private CarCycleInfo cycleInfo;

  @Override
  public String toString() {
    return "CarStop= {" +
        " carIdentity = { " + "mdn=" + carIdentity.getMdn() + ", " + "vrp=" + carIdentity.getVrp() + " }" +
        ", carDevice = { " + "tid=" + carDevice.getTid() + ", " + "mid=" + carDevice.getMid() + ", " + "pv=" + carDevice.getPv() + ", " + "did=" + carDevice.getDid() + " }" +
        ", onTime=" + onTime +
        ", offTime=" + offTime +
        ", cycleInfo=" + cycleInfo +
        " }";
  }
}
