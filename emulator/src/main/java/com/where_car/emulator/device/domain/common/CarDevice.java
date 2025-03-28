package com.where_car.emulator.device.domain.common;

import com.where_car.emulator.global.constants.DomainConstant;
import lombok.Builder;
import lombok.Getter;

/**
 * <pre>
 * 차량 장치 도메인
 * tid : 터미널 아이디 | 관제 타입 별로 구분한다. 차량관제는 tid를 ‘A001’로 사용한다.
 * mid : 제조사 아이디 | 디바이스 제조사에 따라 부여한다. CNSLink 는 ‘6’을 사용한다.
 * pv : 패킷 버전 | M2MM 버전이 5임으로 ‘5’로 사용한다.
 * did : 디바이스 아이디 | GPS로만 운영함으로 ‘1’로 사용한다.
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-27
 */

@Getter
@Builder
public class CarDevice {

  @Builder.Default
  private String tid = DomainConstant.TERMINAL_ID;

  @Builder.Default
  private Integer mid = DomainConstant.MAKE_ID;

  @Builder.Default
  private Integer pv = DomainConstant.PACKET_ID;

  @Builder.Default
  private Integer did = DomainConstant.DEVICE_ID;
}
