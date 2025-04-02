package com.where_car.emulator.device.domain;

import com.where_car.emulator.device.domain.common.CarIdentity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarEntity {

  private CarIdentity carIdentity;
  private Integer totalDistance;
}
