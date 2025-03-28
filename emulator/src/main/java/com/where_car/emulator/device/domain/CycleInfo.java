package com.where_car.emulator.device.domain;

import com.where_car.emulator.device.domain.common.CarCycleInfo;
import com.where_car.emulator.device.domain.common.CarDevice;
import com.where_car.emulator.device.domain.common.CarIdentity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;


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
@Builder
public class CycleInfo {

  private CarIdentity carIdentity;
  private CarDevice carDevice;

  private LocalDateTime oTime;
  private Integer cCnt;

  private List<CarCycleInfo> cList;
}
