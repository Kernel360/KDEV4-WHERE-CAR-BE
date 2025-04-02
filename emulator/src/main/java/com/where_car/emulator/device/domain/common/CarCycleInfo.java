package com.where_car.emulator.device.domain.common;

import lombok.Builder;
import lombok.Getter;

/**
 * <pre>
 *   차량 주기 정보 도메인
 *   sec : 발생시간 (초) | 'ss'
 *   gcd : GPS 상태 | ‘A’ : 정상, ‘V’ : 비정상 ‘0’ : 미장착
 *   lat : GPS 위도 | 위도X1000000한값(소수점6자리)
 *   lon : GPS 경도 | 경도X1000000한값(소수점6자리)
 *   ang : GPS 방향 | 범위: 0~ 365
 *   spd : GPS 속도 | 범위: 0 ~ 255 (단위: km/h)
 *   sum : 누적 주행 거리 | 범위: 0 ~ 9999999 (단위: m)
 *   bat : 배터리 전압 | 범위: 0 ~ 9999 (실제값 x 10, 단위: V)
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-27
 * </pre>
 */


@Getter
@Builder
public class CarCycleInfo {

  private String sec;
  private String gcd;
  private Integer lat;
  private Integer lon;
  private Integer ang;
  private Integer spd;
  private Integer sum;
  private Integer bat;
}
