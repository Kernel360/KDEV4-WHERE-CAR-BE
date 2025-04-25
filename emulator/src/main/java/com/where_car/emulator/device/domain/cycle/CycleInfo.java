package com.where_car.emulator.device.domain.cycle;

import java.util.List;

import com.where_car.emulator.device.domain.car.CarDevice;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * <pre>
 *   주기 정보 전달 도메인
 *   carIdentity: 클래스 주석 참조
 *   carDevice: 클래스 주석 참조
 *   oTime: 발생 시간 | ccyyMMddHHmm
 *   cCnt: 주기 정보 개수 | 1
 *   cList: 주기정보 리스트<carCycleInfo> | 클래스 주석 참조
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-27
 */

@Getter
@ToString
public class CycleInfo {

  private final String mdn;
  private final CarDevice carDevice;

  private final String oTime;
  private final String cCnt;

  private final List<CarCycleInfo> cList;

  @Builder
  public CycleInfo(String mdn, CarDevice carDevice, String oTime, String cCnt, List<CarCycleInfo> cList) {

    if (mdn == null) {
        throw new IllegalArgumentException("MDN 값이 없습니다.");
    }

    this.mdn = mdn;
    this.carDevice = carDevice;
    this.oTime = oTime;
    this.cCnt = cCnt;
    this.cList = cList;
  }
}
