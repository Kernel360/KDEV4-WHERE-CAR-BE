package com.where_car.emulator.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarStartDto {

  private String mdn;
  private String tid;
  private String mid;
  private String pv;
  private String did;
  private String onTime;
  private String offTime;
  private String gcd;
  private String lat;
  private String lon;
  private String ang;
  private String spd;
  private String sum;

  @Override
  public String toString() {
    return "시동 ON 정보= {" +
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
