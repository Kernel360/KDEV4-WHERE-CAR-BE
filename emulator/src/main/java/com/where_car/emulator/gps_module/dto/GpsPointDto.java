package com.where_car.emulator.gps_module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsPointDto {

  private double preLat;
  private double preLon;
  private double curLat;
  private double curLon;
}
