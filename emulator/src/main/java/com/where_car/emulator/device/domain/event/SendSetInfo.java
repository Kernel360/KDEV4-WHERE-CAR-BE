package com.where_car.emulator.device.domain.event;

import com.where_car.emulator.device.domain.car.CarIdentity;

/**
 * <pre>
 *   설정 정보 확인 요청 도메인 | 후순위
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-27
 */
public class SendSetInfo {

  private CarIdentity carIdentity;

  private String oTime;
  private String ctrCnt;
  private String geoCnt;
}
