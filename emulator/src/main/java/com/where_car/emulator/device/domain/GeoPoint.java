package com.where_car.emulator.device.domain;

import com.where_car.emulator.device.domain.common.CarCycleInfo;
import com.where_car.emulator.device.domain.common.CarDevice;
import com.where_car.emulator.device.domain.common.CarIdentity;
import java.time.LocalDateTime;

/**
 * <pre>
 * 지오펜싱 이벤트 정보 전달 | 후순위
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-27
 */
public class GeoPoint {

  private CarIdentity carIdentity;
  private CarDevice carDevice;

  private LocalDateTime onTime;
  private Integer geoGrpId;
  private Integer geoPid;
  private String evtVal;

  private CarCycleInfo cycleInfo;
}
