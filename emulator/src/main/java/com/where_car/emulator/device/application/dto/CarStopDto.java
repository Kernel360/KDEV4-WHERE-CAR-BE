package com.where_car.emulator.device.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
  private String mid;
  private String pv;
  private String did;
  @JsonProperty("onTime")
  private String onTime;
  @JsonProperty("offTime")
  private String offTime;
  private String gcd;
  private String lat;
  private String lon;
  private String ang;
  private String spd;
  private String sum;

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
