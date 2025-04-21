package com.where_car.emulator.device.application.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.where_car.emulator.device.domain.common.CarCycleInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CycleInfoDto {

  private String mdn;
  private String tid;
  private String mid;
  private String pv;
  private String did;
  @JsonProperty("oTime")
  private String oTime;
  @JsonProperty("cCnt")
  private String cCnt;
  @JsonProperty("cList")
  private List<CarCycleInfo> cList;

  @Override
  public String toString() {
    return "주기 정보= {" +
        " mdn='" + mdn +
        ", tid='" + tid +
        ", mid=" + mid +
        ", pv=" + pv +
        ", did=" + did +
        ", oTime=" + oTime +
        ", cCnt=" + cCnt +
        ", cList=" + cList.toString() +
        " }";
  }
}
