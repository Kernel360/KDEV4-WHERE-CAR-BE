package com.where_car.emulator.device.domain;

import com.where_car.emulator.device.domain.common.CarCycleInfo;
import com.where_car.emulator.device.domain.common.CarDevice;
import com.where_car.emulator.device.domain.common.CarIdentity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

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
@Builder
public class CarStart {

  private CarIdentity carIdentity;
  private CarDevice carDevice;

  private LocalDateTime onTime;
  private LocalDateTime offTime;

  private CarCycleInfo cycleInfo;

  @Override
  public String toString() {
    return "CarStart {\n" +
        " carIdentity = { " + "mdn=" + carIdentity.getMdn() + ", " + "vrp=" + carIdentity.getVrp() + " }" +
        "\n carDevice = { " + "tid=" + carDevice.getTid() + ", " + "mid=" + carDevice.getMid() + ", " + "pv=" + carDevice.getPv() + ", " + "did=" + carDevice.getDid() + " }" +
        "\n onTime=" + onTime +
        "\n offTime=" + offTime +
        "\n cycleInfo=" + cycleInfo +
        "\n}";
  }
}
