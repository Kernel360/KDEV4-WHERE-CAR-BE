package com.where_car.emulator.device.domain;

import com.where_car.emulator.device.domain.common.CarDevice;
import com.where_car.emulator.device.domain.common.CarIdentity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

/**
 * <pre>
 *   제어정보 요청 | 후순위
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-27
 */

@Getter
@Builder
public class GetSetInfo {

  private CarIdentity carIdentity;
  private CarDevice carDevice;

  private LocalDateTime onTime;

  @Default
  private String dFWVer = "LTE 1.2";
}
