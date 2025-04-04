package com.where_car.emulator.device.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarStopDto {

  private String mdn;
  private String tid;
  private Integer mid;
  private Integer pv;
  private Integer did;
  private LocalDateTime onTime;
  private LocalDateTime offTime;
  private String gcd;
  private Integer lat;
  private Integer lon;
  private Integer ang;
  private Integer spd;
  private Integer sum;

  @Override
  public String toString() {
    return "시동 OFF 정보= {" +
        " mdn='" + mdn +
        ", tid='" + tid +
        ", mid=" + mid +
        ", pv=" + pv +
        ", did=" + did +
        ", onTime=" + onTime +
        ", offTime=" + offTime +
        ", gcd='" + gcd +
        ", lat=" + lat +
        ", lon=" + lon +
        ", ang=" + ang +
        ", spd=" + spd +
        ", sum=" + sum +
        " }";
  }
}
