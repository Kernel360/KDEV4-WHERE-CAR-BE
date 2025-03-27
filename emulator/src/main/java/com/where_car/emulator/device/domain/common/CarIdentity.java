package com.where_car.emulator.device.domain.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

/**
 * <pre>
 * 차량 식별 도메인
 * mdn : 차량 식별키 | 에뮬레이터에서 사용할 식별키 01012345678
 * vrp : 차량 등록 번호 | 에뮬레이터에서 가지고 있는 차량등록번호 12가3456 <- 에뮬레이터만 사용함
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-27
 */

@Getter
public class CarIdentity {

  @Value("${wherecar.emulator.car-mdn}")
  private String mdn;

  @Value("${wherecar.emulator.car-vrp}")
  private String vrp;
}
