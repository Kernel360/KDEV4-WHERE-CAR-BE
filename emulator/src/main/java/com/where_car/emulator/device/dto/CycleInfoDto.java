package com.where_car.emulator.device.dto;

import com.where_car.emulator.device.domain.common.CarCycleInfo;
import java.util.List;
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
  private String oTime;
  private String cCnt;
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
