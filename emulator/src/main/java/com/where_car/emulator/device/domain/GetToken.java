package com.where_car.emulator.device.domain;

import com.where_car.emulator.device.domain.common.CarDevice;
import com.where_car.emulator.device.domain.common.CarIdentity;

/**
 * <pre>
 * 단말인증 토큰 요청 도메인
 *   carIdentity: 클래스 주석 참조
 *   carDevice: 클래스 주석 참조
 *   dFwVer: 단말 펌웨어 버전 | LTE 1.2
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-27
 */
public class GetToken {

  private CarIdentity carIdentity;
  private CarDevice carDevice;

  private String dFWVer = "LTE 1.2";
}
